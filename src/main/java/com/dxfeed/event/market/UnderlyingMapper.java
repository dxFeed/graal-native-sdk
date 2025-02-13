package com.dxfeed.event.market;

import com.dxfeed.event.option.Underlying;
import com.dxfeed.sdk.events.DxfgEventClazz;
import com.dxfeed.sdk.events.DxfgUnderlying;
import com.dxfeed.sdk.mappers.Mapper;
import org.graalvm.nativeimage.UnmanagedMemory;
import org.graalvm.nativeimage.c.struct.SizeOf;
import org.graalvm.nativeimage.c.type.CCharPointer;

public class UnderlyingMapper extends MarketEventMapper<Underlying, DxfgUnderlying> {

  public UnderlyingMapper(
      final Mapper<String, CCharPointer> stringMapperForMarketEvent
  ) {
    super(stringMapperForMarketEvent);
  }

  @Override
  public DxfgUnderlying createNativeObject() {
    final DxfgUnderlying nObject = UnmanagedMemory.calloc(SizeOf.get(DxfgUnderlying.class));
    nObject.setClazz(DxfgEventClazz.DXFG_EVENT_UNDERLYING.getCValue());
    return nObject;
  }

  @Override
  public void fillNative(final Underlying jObject, final DxfgUnderlying nObject, boolean clean) {
    super.fillNative(jObject, nObject, clean);
    nObject.setEventFlags(jObject.getEventFlags());
    nObject.setIndex(jObject.getIndex());
    nObject.setVolatility(jObject.getVolatility());
    nObject.setFrontVolatility(jObject.getFrontVolatility());
    nObject.setBackVolatility(jObject.getBackVolatility());
    nObject.setCallVolume(jObject.getCallVolume());
    nObject.setPutVolume(jObject.getPutVolume());
    nObject.setPutCallRatio(jObject.getPutCallRatio());
  }

  @Override
  public void cleanNative(final DxfgUnderlying nObject) {
    super.cleanNative(nObject);
  }

  @Override
  protected Underlying doToJava(final DxfgUnderlying nObject) {
    final Underlying jObject = new Underlying();
    this.fillJava(nObject, jObject);
    return jObject;
  }

  @Override
  public void fillJava(final DxfgUnderlying nObject, final Underlying jObject) {
    super.fillJava(nObject, jObject);
    jObject.setEventFlags(nObject.getEventFlags());
    jObject.setIndex(nObject.getIndex());
    jObject.setVolatility(nObject.getVolatility());
    jObject.setFrontVolatility(nObject.getFrontVolatility());
    jObject.setBackVolatility(nObject.getBackVolatility());
    jObject.setCallVolume(nObject.getCallVolume());
    jObject.setPutVolume(nObject.getPutVolume());
    jObject.setPutCallRatio(nObject.getPutCallRatio());
  }
}
