package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.galaxy.DefaultWebResourceFactoryImpl;
import com.github.jmchilton.blend4j.galaxy.WebResourceFactory;

public class ToolShedInstanceFactory {

  public static ToolShedInstance get(final String url, final String apiKey) {
    return get(new DefaultWebResourceFactoryImpl(url, apiKey));
  }

  public static ToolShedInstance get(final WebResourceFactory webResourceFactory) {
    return new ToolShedInstanceImpl(webResourceFactory);
  }
  
  public static ToolShedInstance getMainToolShedInstance() {
    return getMainToolShedInstance(null);
  }
  
  public static ToolShedInstance getMainToolShedInstance(final String apiKey) {
    return get("http://toolshed.g2.bx.psu.edu/", apiKey);
  }

}
