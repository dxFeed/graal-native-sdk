package com.dxfeed.sdk.ipf;

import com.dxfeed.sdk.javac.CPointerPointer;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CPointerTo;

@CContext(Directives.class)
@CPointerTo(DxfgInstrumentProfile2ListPointer.class)
public interface DxfgInstrumentProfile2ListPointerPointer extends
    CPointerPointer<DxfgInstrumentProfile2ListPointer> {

}
