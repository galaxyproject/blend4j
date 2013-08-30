package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.BaseClient;
import com.github.jmchilton.blend4j.galaxy.WebResourceFactory;

class Client extends BaseClient {

  Client(final WebResourceFactory webResourceFactory, final String module) {
    super(webResourceFactory.get(), module);
  }
  
}
