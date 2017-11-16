package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.Config;
import com.github.jmchilton.galaxybootstrap.BootStrapper;
import com.github.jmchilton.galaxybootstrap.BootStrapper.GalaxyDaemon;
import com.github.jmchilton.galaxybootstrap.DownloadProperties;
import com.github.jmchilton.galaxybootstrap.GalaxyData;
import com.github.jmchilton.galaxybootstrap.GalaxyProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestGalaxyInstance {
  private static Properties properties = Config.loadBlendProperties();
  private static GalaxyDaemon galaxyDaemon = null;
  private static String testApiKey = null;
  private static String testUrl = null;
  
  @BeforeSuite
  public static void bootStrapGalaxy() throws URISyntaxException, IOException {
    if(getTestApiKey() == null) {
      DownloadProperties downloadProperties = DownloadProperties.forRelease("v17.01");
      if(Boolean.getBoolean(System.getProperty("galaxy.bootstrap.github", "false"))){
        downloadProperties = DownloadProperties.wgetGithubMaster();
      }
      final BootStrapper bootStrapper = new BootStrapper(downloadProperties);
      bootStrapper.setupGalaxy();
      final GalaxyProperties galaxyProperties = 
        new GalaxyProperties()
              .assignFreePort()
              .prepopulateSqliteDatabase()
              .configureNestedShedTools();
      final GalaxyData galaxyData = new GalaxyData();
      final GalaxyData.User adminUser = new GalaxyData.User("admin@localhost");
      final GalaxyData.User normalUser = new GalaxyData.User("user@localhost");
      galaxyData.getUsers().add(adminUser);
      galaxyData.getUsers().add(normalUser);
      galaxyProperties.setAdminUser("admin@localhost");
      galaxyProperties.setAppProperty("allow_library_path_paste", "true");
      galaxyProperties.setAppProperty("library_import_dir", ".");
      galaxyProperties.setAppProperty("tool_dependency_dir", "tool_dependencies");
      final int port = galaxyProperties.getPort();
      
      buildTestTools(galaxyProperties, bootStrapper.getPath());
      
      testApiKey = adminUser.getApiKey();
      testUrl = String.format("http://localhost:%d/", port);
      
      galaxyDaemon = bootStrapper.run(galaxyProperties, galaxyData);
      galaxyDaemon.waitForUp();
    }
  }

  private static void copyFile(File source, File dest) throws IOException {
    FileChannel sourceChannel = null;
    FileChannel destChannel = null;
    try {
      sourceChannel = new FileInputStream(source).getChannel();
      destChannel = new FileOutputStream(dest).getChannel();
      destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    }finally{
      sourceChannel.close();
      destChannel.close();
    }
  }
  
  /**
   * Copies over necessary files for extra tools in Galaxy for testing.
   * @param galaxyProperties  The GalaxyProperties object.
   * @param galaxyRootPath  The root path of the Galaxy directory.
   * @throws URISyntaxException 
   * @throws IOException 
   */
  private static void buildTestTools(GalaxyProperties galaxyProperties, String galaxyRootPath) throws URISyntaxException, IOException {
    File collectionExampleToolSource = new File(TestGalaxyInstance.class.getResource(
        "collection_list.xml").toURI());
    File testToolConfigSource = new File(TestGalaxyInstance.class.getResource(
        "tool_conf_test.xml").toURI());

    File galaxyRootFile = new File(galaxyRootPath);

    // copy over necessary files for testing custom tools
    File collectionExampleToolDirectory = new File(galaxyRootPath,
        "tools/collection");
    collectionExampleToolDirectory.mkdirs();
    File collectionExampleToolDestination = 
        new File(collectionExampleToolDirectory,"collection_list.xml");
    copyFile(collectionExampleToolSource, collectionExampleToolDestination);

    File testToolConfigDestination = new File(galaxyRootFile, "config/tool_conf_test.xml");
    copyFile(testToolConfigSource, testToolConfigDestination);

    // set configuration file in Galaxy for custom tools
    galaxyProperties.setAppProperty("tool_config_file",
        "config/tool_conf.xml.sample,config/shed_tool_conf.xml.sample,tool_conf_test.xml");
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
    return GalaxyInstanceFactory.get(galaxyInstanceUrl, galaxyApiKey, true);
  }

  static String getTestApiKey() {
    return getProperty("test.galaxy.key", testApiKey);
  }

  static String getTestInstanceUrl() {
    return getProperty("test.galaxy.instance", testUrl);
  }

  static String getProperty(final String key, final String defaultValue) {
    String value = defaultValue;
    if(System.getProperties().getProperty(key) != null) {
      value = System.getProperties().getProperty(key);
    } else if(properties.containsKey(key)) {
      value = properties.getProperty(key);
    }
    return value;
  }
}
