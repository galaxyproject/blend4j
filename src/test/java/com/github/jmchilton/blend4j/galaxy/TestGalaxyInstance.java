package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.Config;
import com.github.jmchilton.galaxybootstrap.BootStrapper;
import com.github.jmchilton.galaxybootstrap.BootStrapper.GalaxyDaemon;
import com.github.jmchilton.galaxybootstrap.GalaxyData;
import com.github.jmchilton.galaxybootstrap.GalaxyProperties;

import java.util.Properties;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestGalaxyInstance {
  private static Properties properties = Config.loadBlendProperties();
  private static GalaxyDaemon galaxyDaemon = null;
  private static String testApiKey = null;
  private static String testUrl = null;
  
  @BeforeSuite
  public static void bootStrapGalaxy() {
    if(getTestApiKey() == null) {
      final BootStrapper bootStrapper = new BootStrapper();
      bootStrapper.setupGalaxy();
      final GalaxyProperties galaxyProperties = new GalaxyProperties().assignFreePort();
      final GalaxyData galaxyData = new GalaxyData();
      final GalaxyData.User adminUser = new GalaxyData.User("admin@localhost");
      final GalaxyData.User normalUser = new GalaxyData.User("user@localhost");
      galaxyData.getUsers().add(adminUser);
      galaxyData.getUsers().add(normalUser);
      galaxyProperties.setAdminUser("admin@localhost");
      galaxyProperties.setAppProperty("allow_library_path_paste", "true");
      galaxyProperties.setAppProperty("use_remote_user", "true");
      galaxyProperties.setAppProperty("library_import_dir", ".");
      final int port = galaxyProperties.getPort();
      
      testApiKey = adminUser.getApiKey();
      testUrl = String.format("http://localhost:%d/", port);
      
      galaxyDaemon = bootStrapper.run(galaxyProperties, galaxyData);
      galaxyDaemon.waitForUp();
    }
  }

  @AfterSuite
  public static void cleanUpGalaxy() {
    if(galaxyDaemon != null) {
      galaxyDaemon.stop();
      galaxyDaemon.waitForDown();
      //galaxyDaemon.getBootStrapper().deleteGalaxyRoot();
    }
  }
  
  static GalaxyInstance get() {
    final String galaxyInstanceUrl = getTestInstanceUrl();
    final String galaxyApiKey = getTestApiKey();
    return GalaxyInstanceFactory.get(galaxyInstanceUrl, galaxyApiKey);
  }

  static String getTestApiKey() {
    return getProperty("test.galaxy.key", testApiKey);
  }

  static String getTestInstanceUrl() {
    return getProperty("test.galaxy.instance", testUrl);
  }

  static String getProperty(final String key, final String defaultValue) {
    String value = defaultValue;
    if(System.getProperties().contains(key)) {
      value = System.getProperties().getProperty(key);
    } else if(properties.containsKey(key)) {
      value = properties.getProperty(key);
    }
    return value;
  }
}
