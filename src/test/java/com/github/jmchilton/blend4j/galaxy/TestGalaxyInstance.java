package com.github.jmchilton.blend4j.galaxy;

import java.util.Properties;

import com.github.jmchilton.blend4j.Config;

public class TestGalaxyInstance {
  
  public static GalaxyInstance get() {
    final Properties properties = Config.loadBlendProperties();
    String galaxyInstanceUrl = "https://main.g2.bx.psu.edu/"; 
    if(properties.containsKey("test.galaxy.instance")) {
      galaxyInstanceUrl = properties.getProperty("test.galaxy.instance");
    }
    // API key for an account created to test blend4j
    String galaxyApiKey = "274f4583a821d8fff923ac6ab5e1e030"  ;
    if(properties.containsKey("test.galaxy.key")) {
      galaxyApiKey = properties.getProperty("test.galaxy.key");
    }
    return GalaxyInstanceFactory.get(galaxyInstanceUrl, galaxyApiKey);
  }
  
}
