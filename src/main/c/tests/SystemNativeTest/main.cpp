#include <catch.hpp>
#include <dxfg_api.h>
#include <thread>

namespace dxfg {
namespace test {

TEST_CASE("Check reads unset property", "[SystemNative]") {
    // Setup graal.
    graal_isolate_t *isolate = nullptr;
    graal_isolatethread_t *thread = nullptr;
    auto graal_result = graal_create_isolate(nullptr, &isolate, &thread);
    REQUIRE(graal_result == 0);

    // On getting an unset property, returns nullptr.
    auto prop = dxfg_system_get_property(thread, "first_prop");
    REQUIRE(prop == nullptr);
    // Frees nullptr does not result in an error.
    dxfg_utils_free(thread, (void *)(prop));

    // Tear down graal.
    graal_result = graal_detach_all_threads_and_tear_down_isolate(thread);
    REQUIRE(graal_result == 0);
}

TEST_CASE("Check write/read property", "[SystemNative]") {
    // Setup graal.
    graal_isolate_t *isolate = nullptr;
    graal_isolatethread_t *thread = nullptr;
    auto graal_result = graal_create_isolate(nullptr, &isolate, &thread);
    REQUIRE(graal_result == 0);

    size_t count = 100;
    std::string key = "key_";
    std::string val = "val_";

    // Sets properties.
    for (size_t i = 0; i < count; ++i) {
        auto currentKey = std::string(key + std::to_string(i));
        auto currentVal = std::string(val + std::to_string(i));
        auto res = dxfg_system_set_property(thread, currentKey.c_str(), currentVal.c_str());
        REQUIRE(res == DXFG_EC_SUCCESS);
    }

    // Gets properties.
    for (size_t i = 0; i < count; ++i) {
        auto currentKey = std::string(key + std::to_string(i));
        auto expectedVal = std::string(val + std::to_string(i));
        auto currentVal = dxfg_system_get_property(thread, currentKey.c_str());
        REQUIRE(currentVal != nullptr);
        REQUIRE(std::string(currentVal) == expectedVal);
        dxfg_utils_free(thread, (void *)currentVal);
    }

    // Tear down graal.
    graal_result = graal_detach_all_threads_and_tear_down_isolate(thread);
    REQUIRE(graal_result == 0);
}

TEST_CASE("Check in different isolate", "[SystemNative]") {
    // Setup graal.
    graal_isolate_t *isolateFirst = nullptr;
    graal_isolatethread_t *threadFirst = nullptr;
    graal_isolate_t *isolateSecond = nullptr;
    graal_isolatethread_t *threadSecond = nullptr;
    auto graal_result = graal_create_isolate(nullptr, &isolateFirst, &threadFirst);
    REQUIRE(graal_result == 0);
    graal_result = graal_create_isolate(nullptr, &isolateSecond, &threadSecond);
    REQUIRE(graal_result == 0);

    size_t count = 100;
    std::string key = "key_";
    std::string valFirst = "val_first_";
    std::string valSecond = "val_second_";

    // Sets properties.
    for (size_t i = 0; i < count; ++i) {
        auto currentKey = std::string(key + std::to_string(i));
        auto currentValFirst = std::string(valFirst + std::to_string(i));
        auto currentValSecond = std::string(valSecond + std::to_string(i));
        auto res = dxfg_system_set_property(threadFirst, currentKey.c_str(), currentValFirst.c_str());
        REQUIRE(res == DXFG_EC_SUCCESS);
        res = dxfg_system_set_property(threadSecond, currentKey.c_str(), currentValSecond.c_str());
        REQUIRE(res == DXFG_EC_SUCCESS);
    }

    // Gets properties.
    for (size_t i = 0; i < count; ++i) {
        auto currentKey = std::string(key + std::to_string(i));
        auto expectedValFirst = std::string(valFirst + std::to_string(i));
        auto expectedValSecond = std::string(valSecond + std::to_string(i));

        auto currentVal = dxfg_system_get_property(threadFirst, currentKey.c_str());
        REQUIRE(currentVal != nullptr);
        REQUIRE(std::string(currentVal) == expectedValFirst);
        dxfg_utils_free(threadFirst, (void *)currentVal);

        currentVal = dxfg_system_get_property(threadSecond, currentKey.c_str());
        REQUIRE(currentVal != nullptr);
        REQUIRE(std::string(currentVal) == expectedValSecond);
        dxfg_utils_free(threadFirst, (void *)currentVal);
    }

    // Tear down graal.
    graal_result = graal_detach_all_threads_and_tear_down_isolate(threadFirst);
    REQUIRE(graal_result == 0);

    graal_result = graal_detach_all_threads_and_tear_down_isolate(threadSecond);
    REQUIRE(graal_result == 0);
}

} // namespace test
} // namespace dxfg