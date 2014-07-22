package com.github.jmchilton.blend4j.galaxy;


/**
 * This class contains static factory methods for producing GalaxyInstance objects.
 * These objects in turn can be used to produce client's for specific Galaxy API
 * endpoints such as workflows and histories.
 * 
 * GalaxyInstance objects can be created from a URL and API key using the {@link get}
 * method or from an e-mail address and password using {@link getFromCredentials}.
 * 
 * Finally {@link get} can be supplied with a {@link WebResourceFactory} to control
 * the connection parameters at a low level - this should not be needed under normal
 * circumstances and requires a greater knowledge of the underlying Jersey library
 * to use.
 * 
 * @author John Chilton
 */
public class GalaxyInstanceFactory {

  public static GalaxyInstance get(final String url, final String apiKey) {
    return get(new DefaultWebResourceFactoryImpl(url, apiKey));
  }

  public static GalaxyInstance get(final String url, final String apiKey, final boolean debug) {
    return get(new DefaultWebResourceFactoryImpl(url, apiKey, debug));
  }
  
  public static GalaxyInstance getFromCredentials(final String url, final String email, final String password) {
    return get(new GalaxyAuthWebResourceFactoryImpl(url, email, password));
  }

  public static GalaxyInstance getFromCredentials(final String url, final String email, final String password, final boolean debug) {
    return get(new GalaxyAuthWebResourceFactoryImpl(url, email, password, debug));
  }

  public static GalaxyInstance get(final WebResourceFactory webResourceFactory) {
    return new GalaxyInstanceImpl(webResourceFactory);
  }
}
