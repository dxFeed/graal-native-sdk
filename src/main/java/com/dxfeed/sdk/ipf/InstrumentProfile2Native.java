package com.dxfeed.sdk.ipf;

import com.dxfeed.sdk.NativeUtils;
import com.dxfeed.sdk.exception.ExceptionHandlerReturnMinusOne;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CConst;

@CContext(Directives.class)
public class InstrumentProfile2Native {

  @CEntryPoint(
      name = "dxfg_instrument_profile_free",
      exceptionHandler = ExceptionHandlerReturnMinusOne.class
  )
  public static int dxfgInstrumentProfileFree(final IsolateThread ignoredThread,
      DxfgInstrumentProfile2Pointer instrumentProfile) {
    if (instrumentProfile.isNonNull()) {
      NativeUtils.MAPPER_INSTRUMENT_PROFILE_2.release(instrumentProfile);
      UnmanagedMemory.free(instrumentProfile);
    }

    return ExceptionHandlerReturnMinusOne.EXECUTE_SUCCESSFULLY;
  }

  @CEntryPoint(
      name = "dxfg_instrument_profile_free_cached",
      exceptionHandler = ExceptionHandlerReturnMinusOne.class
  )
  public static int dxfgInstrumentProfileFreeCached(final IsolateThread ignoredThread,
      DxfgInstrumentProfile2Pointer instrumentProfile) {
    if (instrumentProfile.isNonNull()) {
      NativeUtils.MAPPER_INSTRUMENT_PROFILE_2_CACHED.release(instrumentProfile);
      UnmanagedMemory.free(instrumentProfile);
    }

    return ExceptionHandlerReturnMinusOne.EXECUTE_SUCCESSFULLY;
  }

  @CEntryPoint(
      name = "dxfg_instrument_profiles_array_free",
      exceptionHandler = ExceptionHandlerReturnMinusOne.class
  )
  public static int dxfgInstrumentProfilesArrayFree(final IsolateThread ignoredThread,
      @CConst DxfgInstrumentProfile2Pointer instrumentProfiles, int size) {
    if (instrumentProfiles.isNonNull() && size > 0) {
      for (int i = 0; i < size; i++) {
        NativeUtils.MAPPER_INSTRUMENT_PROFILE_2.release(instrumentProfiles.addressOf(i));
      }

      UnmanagedMemory.free(instrumentProfiles);
    }

    return ExceptionHandlerReturnMinusOne.EXECUTE_SUCCESSFULLY;
  }

  @CEntryPoint(
      name = "dxfg_instrument_profiles_array_free_cached",
      exceptionHandler = ExceptionHandlerReturnMinusOne.class
  )
  public static int dxfgInstrumentProfilesArrayFreeCached(final IsolateThread ignoredThread,
      @CConst DxfgInstrumentProfile2Pointer instrumentProfiles, int size) {
    if (instrumentProfiles.isNonNull() && size > 0) {
      for (int i = 0; i < size; i++) {
        NativeUtils.MAPPER_INSTRUMENT_PROFILE_2_CACHED.release(instrumentProfiles.addressOf(i));
      }

      UnmanagedMemory.free(instrumentProfiles);
    }

    return ExceptionHandlerReturnMinusOne.EXECUTE_SUCCESSFULLY;
  }
}
