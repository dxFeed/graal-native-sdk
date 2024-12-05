// Copyright (c) 2024 Devexperts LLC.
// SPDX-License-Identifier: MPL-2.0

#pragma once

#include <dxfg_api.h>

#include "CommandLineParser.hpp"
#include "CommandsContext.hpp"
#include "CommandsRegistry.hpp"

#include <chrono>
#include <cstdio>
#include <string>
#include <thread>
#include <vector>

void finalize(graal_isolatethread_t * /* thread */, void * /* user_data */);
void printEvents(graal_isolatethread_t *isolateThread, dxfg_event_type_list *events, void * /* user_data */);
void endpointStateChangeListener(graal_isolatethread_t * /* thread */, dxfg_endpoint_state_t old_state,
                                 dxfg_endpoint_state_t new_state, void * /* user_data */);

namespace dxfg {
Command finalizeListenerCase{
    "FinalizeListenerCase",
    {"fl"},
    "",
    "fl [<properties>] [<address>]",
    {"fl %defaultAddress%"},
    [](const Command & /*self*/, graal_isolatethread_t *isolateThread, const std::vector<std::string> &args,
       const dxfg::CommandsContext &context) {
        using namespace std::chrono_literals;

        puts("== FinalizeListener::BEGIN ==");

        std::size_t argIndex = 0;
        auto address = dxfg::CommandLineParser::parseAddress(args, argIndex, context.getDefaultAddress());

        dxfg_endpoint_t *endpoint = dxfg_DXEndpoint_create(isolateThread);
        dxfg_endpoint_state_change_listener_t *stateListener =
            dxfg_PropertyChangeListener_new(isolateThread, &endpointStateChangeListener, nullptr);
        dxfg_Object_finalize(isolateThread, reinterpret_cast<dxfg_java_object_handler *>(stateListener), finalize,
                             nullptr);
        dxfg_DXEndpoint_addStateChangeListener(isolateThread, endpoint, stateListener);
        dxfg_DXEndpoint_connect(isolateThread, endpoint, address.c_str());
        dxfg_feed_t *feed = dxfg_DXEndpoint_getFeed(isolateThread, endpoint);
        dxfg_subscription_t *subscriptionQuote = dxfg_DXFeed_createSubscription(isolateThread, feed, DXFG_EVENT_QUOTE);
        dxfg_feed_event_listener_t *listener = dxfg_DXFeedEventListener_new(isolateThread, &printEvents, nullptr);
        dxfg_Object_finalize(isolateThread, reinterpret_cast<dxfg_java_object_handler *>(listener), finalize, nullptr);
        dxfg_DXFeedSubscription_addEventListener(isolateThread, subscriptionQuote, listener);

        printf(" getState = %d\n", dxfg_DXEndpoint_getState(isolateThread, endpoint));

        dxfg_DXFeedSubscription_close(isolateThread, subscriptionQuote);
        dxfg_DXEndpoint_close(isolateThread, endpoint);
        dxfg_JavaObjectHandler_release(isolateThread, &subscriptionQuote->handler);
        dxfg_JavaObjectHandler_release(isolateThread, &listener->handler);
        dxfg_JavaObjectHandler_release(isolateThread, &feed->handler);
        dxfg_JavaObjectHandler_release(isolateThread, &stateListener->handler);
        dxfg_JavaObjectHandler_release(isolateThread, &endpoint->handler);
        dxfg_gc(isolateThread);

        std::this_thread::sleep_for(2s);

        dxfg_gc(isolateThread);

        std::this_thread::sleep_for(2s);

        puts("== FinalizeListener::END ==");
    }};
} // namespace dxfg