package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.WebResource;

class GalaxyInstanceImpl implements GalaxyInstance {
  private final WebResourceFactory webResourceFactory;
  
  GalaxyInstanceImpl(final WebResourceFactory webResourceFactory) {
    this.webResourceFactory = webResourceFactory;
  }
  
  public HistoriesClient getHistoriesClient() {
    return new HistoriesClientImpl(this);
  }

  public LibrariesClient getLibrariesClient() {
    return new LibrariesClientImpl(this);
  }
  
  public UsersClient getUsersClient() {
    return new UsersClientImpl(this);
  }
  
  public WorkflowsClient getWorkflowsClient() {
    return new WorkflowsClientImpl(this);
  }
  
  public RolesClient getRolesClient() {
    return new RolesClientImpl(this);
  }

  public ToolsClient getToolsClient() {
    return new ToolsClientImpl(this);
  }
  
  public WebResource getWebResource() {
    return webResourceFactory.get();
  }

  public String getGalaxyUrl() {
    if(webResourceFactory instanceof DefaultWebResourceFactoryImpl) {
      return ((DefaultWebResourceFactoryImpl) webResourceFactory).getGalaxyUrl();
    } else {
      final String uriString = webResourceFactory.get().getURI().toString();
      return uriString.substring(0, uriString.length() - "/api".length());
    }
  }

}
