package com.dxfeed.api.feed;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.c.CContext;

class Directives implements CContext.Directives {

  @Override
  public List<String> getHeaderFiles() {
    return Collections.singletonList("\"" + Path.of(System.getProperty("project.path"),"/src/main/c/api/dxfg_feed.h").toAbsolutePath() + "\"");
  }
}
