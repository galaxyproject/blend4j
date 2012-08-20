package com.github.jmchilton.blend4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
  
  public static Properties loadBlendProperties() {
    final Properties properties = new Properties();
    final File blendPropertiesFile = getBlendPropertiesFile();
    if(blendPropertiesFile != null) {
      FileInputStream fileInputStream = null;
      try {
        fileInputStream = new FileInputStream(blendPropertiesFile);
        properties.load(fileInputStream);
      } catch(IOException e) {
        e.printStackTrace();
      } finally {
        try { 
          if(fileInputStream != null) {
            fileInputStream.close();
          }
        } catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
    return properties;
  }

  private static File getBlendPropertiesFile() {
    final String homeDir = System.getProperty("user.home");
    File blendPropertiesFile = new File(homeDir, ".blend.properties");
    if(!blendPropertiesFile.exists()) {
      blendPropertiesFile = null;
    }
    return blendPropertiesFile;
  }
  
}
