package com.dxfeed.sdk.mappers;

import com.dxfeed.schedule.Session;
import com.dxfeed.sdk.schedule.DxfgSession;
import com.dxfeed.sdk.schedule.DxfgSessionList;
import com.dxfeed.sdk.schedule.DxfgSessionPointer;
import org.graalvm.nativeimage.c.struct.SizeOf;

public class ListSessionMapper
    extends ListMapper<Session, DxfgSession, DxfgSessionPointer, DxfgSessionList> {

  private final Mapper<Session, DxfgSession> mapper;

  public ListSessionMapper(final Mapper<Session, DxfgSession> mapper) {
    this.mapper = mapper;
  }

  @Override
  protected Session toJava(final DxfgSession nObject) {
    return this.mapper.toJava(nObject);
  }

  @Override
  protected DxfgSession toNative(final Session jObject) {
    return this.mapper.toNative(jObject);
  }

  @Override
  protected void releaseNative(final DxfgSession nObject) {
    this.mapper.release(nObject);
  }

  @Override
  protected int getNativeListSize() {
    return SizeOf.get(DxfgSessionList.class);
  }
}
