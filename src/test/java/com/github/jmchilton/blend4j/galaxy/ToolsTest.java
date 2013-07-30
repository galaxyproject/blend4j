package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.ToolsClient.FileUploadRequest;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToolsTest {
  private GalaxyInstance instance;
  private ToolsClient client;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    client = instance.getToolsClient();
  }

  @Test
  public void testUpload() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final FileUploadRequest request = new FileUploadRequest(historyId, testFile);
    final ClientResponse clientResponse = client.uploadRequest(request);
    //assert clientResponse.getStatus() == 200 : clientResponse.getEntity(String.class);
  }

}
