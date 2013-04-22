package com.github.jmchilton.blend4j.galaxy;

public class GalaxyInstanceFactory {
  public static GalaxyInstance get(final String url, final String apiKey) {
    return get(new DefaultWebResourceFactoryImpl(url, apiKey));
  }

  public static GalaxyInstance get(final WebResourceFactory webResourceFactory) {
    return new GalaxyInstanceImpl(webResourceFactory);
  }
}
