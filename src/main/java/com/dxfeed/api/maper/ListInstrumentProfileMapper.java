package com.dxfeed.api.maper;

import com.dxfeed.api.ipf.DxfgInstrumentProfile;
import com.dxfeed.api.ipf.DxfgInstrumentProfileList;
import com.dxfeed.api.ipf.DxfgInstrumentProfilePointer;
import com.dxfeed.ipf.InstrumentProfile;
import org.graalvm.nativeimage.c.struct.SizeOf;

public class ListInstrumentProfileMapper
    extends ListMapper<InstrumentProfile, DxfgInstrumentProfile, DxfgInstrumentProfilePointer, DxfgInstrumentProfileList> {

  private final Mapper<InstrumentProfile, DxfgInstrumentProfile> mapper;

  public ListInstrumentProfileMapper(
      final Mapper<InstrumentProfile, DxfgInstrumentProfile> mapper) {
    this.mapper = mapper;
  }

  @Override
  protected InstrumentProfile toJava(final DxfgInstrumentProfile nObject) {
    return this.mapper.toJava(nObject);
  }

  @Override
  protected DxfgInstrumentProfile toNative(final InstrumentProfile jObject) {
    return this.mapper.toNative(jObject);
  }

  @Override
  protected void releaseNative(final DxfgInstrumentProfile nObject) {
    this.mapper.release(nObject);
  }

  @Override
  protected int getSizeCList() {
    return SizeOf.get(DxfgInstrumentProfileList.class);
  }
}
