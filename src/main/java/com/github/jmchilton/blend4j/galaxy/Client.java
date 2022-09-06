package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.BaseClient;
import com.github.jmchilton.blend4j.exceptions.ResponseException;
import com.github.jmchilton.blend4j.galaxy.beans.HasGalaxyUrl;
import com.sun.jersey.api.client.ClientResponse;

class Client extends BaseClient {
  private final GalaxyInstanceImpl galaxyInstance;

  Client(final GalaxyInstanceImpl galaxyInstance, final String module) {
    super(galaxyInstance.getWebResource(), module);
    this.galaxyInstance = galaxyInstance;
  }

  @Override
  protected ResponseException buildResponseException(final ClientResponse clientResponse) {
    final ResponseException exception = new GalaxyResponseException(clientResponse);
    return exception;
  }
  
  GalaxyInstance getGalaxyInstance() {
    return galaxyInstance;
  }
  
  protected <T extends HasGalaxyUrl> T setGalaxyUrl(final T bean) {
    System.out.println("Rimsha U2");
    bean.setGalaxyUrl(galaxyInstance.getGalaxyUrl());
    bean.setApiKey(galaxyInstance.getApiKey());
    return bean;
  }

}
