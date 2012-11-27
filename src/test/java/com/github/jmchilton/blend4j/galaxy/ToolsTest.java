package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    final String historyId = getTestHistoryId();
    final File testFile = getTestFile();
    final ClientResponse clientResponse = client.fileUploadRequest(historyId, "txt", "?", testFile);
    //assert clientResponse.getStatus() == 200 : clientResponse.getEntity(String.class);
  }
  
  private String getTestHistoryId() {
    final History testHistory = new History();
    testHistory.setName("Upload Test History");
    final History newHistory = instance.getHistoriesClient().create(testHistory);
    final String historyId = newHistory.getId();
    return historyId;
  }
  
  private File getTestFile() {
    try {
      final File tempFile = File.createTempFile("galxtest", ".txt");
      final FileWriter writer = new FileWriter(tempFile);
      try {
        writer.write("Hello World!!!");
      } finally {
        writer.close();
      }
      return tempFile;
    } catch(final IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
  
}
