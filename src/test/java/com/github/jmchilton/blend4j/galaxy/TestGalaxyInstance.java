package com.github.jmchilton.blend4j.galaxy;

import java.util.Properties;

import com.github.jmchilton.blend4j.Config;

class TestGalaxyInstance {
  private static Properties properties = Config.loadBlendProperties();
  
  static GalaxyInstance get() {
    final String galaxyInstanceUrl = getTestInstanceUrl(); 
    final String galaxyApiKey = getTestApiKey();
    return GalaxyInstanceFactory.get(galaxyInstanceUrl, galaxyApiKey);
  }

  static String getTestApiKey() {
    return getProperty("test.galaxy.key", "274f4583a821d8fff923ac6ab5e1e030");
  }

  static String getTestInstanceUrl() {
    return getProperty("test.galaxy.instance", "https://main.g2.bx.psu.edu/");
  }

  static String getProperty(final String key, final String defaultValue) {
    String value = defaultValue;
    if(properties.containsKey(key)) { 
      value = properties.getProperty(key);
    }
    return value;
  }
  
}
