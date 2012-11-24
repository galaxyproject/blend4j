package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Default and simplest possible implementation of WebResourceFactory.
 * 
 * @author John Chilton (jmchilton@gmail.com)
 *
 */
public class DefaultWebResourceFactoryImpl implements WebResourceFactory {
  public static String API_PATH = "api";
  private String url;
  private String key;
  
  public DefaultWebResourceFactoryImpl(final String url, final String key) {
    this.url = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    this.key = key;
  }
  
  /**
   * @return A Jersey WebResource setup to target 
   * http://<galaxy_url>/api and with the key query parameter set to desired Galaxy key.
   */
  public WebResource get() {
    final com.sun.jersey.api.client.Client client = getJerseyClient();
    return client.resource(getGalaxyUrl()).queryParam("key", getKey()).path(API_PATH);
  }
  
  String getGalaxyUrl() {
    return url;
  }
  
  protected String getKey() {
    return key;
  }
  
  /**
   * Return a configured Jersey client for Galaxy API code to interact with. If this method is overridden 
   * ensure FEATURE_POJO_MAPPING is enabled.
   * 
   * clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
   * 
   * @return Jersey client.
   * 
   */
  protected com.sun.jersey.api.client.Client getJerseyClient() {
    final ClientConfig clientConfig = new DefaultClientConfig() ;
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    return com.sun.jersey.api.client.Client.create(clientConfig);    
  }
  
}
