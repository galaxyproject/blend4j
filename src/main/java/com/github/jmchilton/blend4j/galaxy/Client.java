package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.BaseClient;
import com.github.jmchilton.blend4j.galaxy.beans.HasGalaxyUrl;

class Client extends BaseClient {
  private final GalaxyInstanceImpl galaxyInstance;

  Client(final GalaxyInstanceImpl galaxyInstance, final String module) {
    super(galaxyInstance.getWebResource(), module);
    this.galaxyInstance = galaxyInstance;
  }

  GalaxyInstance getGalaxyInstance() {
    return galaxyInstance;
  }
  
  protected <T extends HasGalaxyUrl> T setGalaxyUrl(final T bean) {
    bean.setGalaxyUrl(galaxyInstance.getGalaxyUrl());
    bean.setApiKey(galaxyInstance.getApiKey());
    return bean;
  }

}
