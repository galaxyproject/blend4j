package com.github.jmchilton.blend4j.galaxy;

import java.util.Map;
import org.testng.annotations.Test;

public class ConfigurationTest {

  @Test
  public void testPathPaste() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final ConfigurationClient client = galaxyInstance.getConfigurationClient();
    final Map<String, Object> config = client.getRawConfiguration();
    assert config.containsKey("terms_url");
  }

}
