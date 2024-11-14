// Copyright (c) 2024 Devexperts LLC.
// SPDX-License-Identifier: MPL-2.0

package com.dxfeed.sdk.glossary;

import com.dxfeed.sdk.javac.CPointerPointer;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CPointerTo;

@CContext(Directives.class)
@CPointerTo(DxfgAdditionalUnderlyingsHandle.class)
public interface DxfgAdditionalUnderlyingsHandlePointer extends
    CPointerPointer<DxfgAdditionalUnderlyingsHandle> {

}
