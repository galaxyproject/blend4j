package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.ToolsClient.FileUploadRequest;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.List;
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
  public void testUploadRequest() {
    final FileUploadRequest request = testRequest();
    final ClientResponse clientResponse = client.uploadRequest(request);
    assert clientResponse.getStatus() == 200;
    //final String reply = clientResponse.getEntity(String.class);
    //assert false: reply;
  }
  
  @Test
  public void testUpload() {
    final FileUploadRequest request = testRequest();
    final ToolExecution toolExecution = client.upload(request);
    final List<OutputDataset> datasets = toolExecution.getOutputs();
    assert ! datasets.isEmpty();
    assert datasets.get(0).getOutputName().equals("output0");
    
    //final ClientResponse clientResponse = client.uploadRequest(request);
    //assert clientResponse.getStatus() == 200;
    //final String reply = clientResponse.getEntity(String.class);
    //assert false: reply;
  }
    
  private FileUploadRequest testRequest() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final FileUploadRequest request = new FileUploadRequest(historyId, testFile);
    return request;
  }

}
