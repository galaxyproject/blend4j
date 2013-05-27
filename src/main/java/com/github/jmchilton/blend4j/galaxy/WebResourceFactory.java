package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.WebResource;

public interface WebResourceFactory {
  /**
   * @return A Jersey WebResource setup to target http://<galaxy_url>/api and with the key query parameter set to
   * desired Galaxy key.
   */
  public WebResource get();
  
  public String getGalaxyUrl();
  
  public String getApiKey();

}
