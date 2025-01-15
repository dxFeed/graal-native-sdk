package com.dxfeed.sdk.ipf;

import com.dxfeed.sdk.javac.CPointerPointer;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CPointerTo;

@CContext(Directives.class)
@CPointerTo(DxfgInstrumentProfile2Pointer.class)
public interface DxfgInstrumentProfilePointerPointer extends
    CPointerPointer<DxfgInstrumentProfile2Pointer> {

}
