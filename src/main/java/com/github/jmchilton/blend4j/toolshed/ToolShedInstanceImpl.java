package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.galaxy.WebResourceFactory;

class ToolShedInstanceImpl implements ToolShedInstance {
  private WebResourceFactory webResourceFactory;
  
  ToolShedInstanceImpl(final WebResourceFactory webResourceFactory) {
    this.webResourceFactory = webResourceFactory; 
  }

  public RepositoriesClient getRepositoriesClient() {
    return new RepositoriesClientImpl(webResourceFactory);
  }
  
}
