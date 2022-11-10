package com.dxfeed.api.events;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.struct.CField;
import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.nativeimage.c.type.CCharPointer;

@CContext(Directives.class)
@CStruct("dxfg_order_t")
public interface DxfgOrder extends DxfgOrderBase {

  @CField("market_maker")
  CCharPointer getMarketMaker();

  @CField("market_maker")
  void setMarketMaker(CCharPointer value);
}
