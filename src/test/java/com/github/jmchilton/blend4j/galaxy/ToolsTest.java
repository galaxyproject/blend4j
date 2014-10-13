package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.ToolsClient.FileUploadRequest;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public void testRandomLinesCall() throws InterruptedException {
    final History history = TestHelpers.createTestHistoryObject(instance, "randomlineshistorytest");
    final String inputId = TestHelpers.populateTestDataset(instance, history.getId(), "1\n2\n3\n");

    final Map<String, Object> parameters = new HashMap<String, Object>();
    final Map<String, String> inputDict = new HashMap<String, String>();
    inputDict.put("src", "hda");
    inputDict.put("id", inputId);
    parameters.put("input", inputDict);
    parameters.put("num_lines", "1");
    final ToolInputs toolInputs = new ToolInputs("random_lines1", parameters);
    client.create(history, toolInputs);
  }

  @Test
  public void testUploadRequest() {
    final FileUploadRequest request = testRequest();
    final ClientResponse clientResponse = client.uploadRequest(request);
    assert clientResponse.getStatus() == 200;
  }

  @Test
  public void testUpload() {
    final FileUploadRequest request = testRequest();
    final ToolExecution toolExecution = client.upload(request);
    final List<OutputDataset> datasets = toolExecution.getOutputs();
    assert !datasets.isEmpty();
    assert datasets.get(0).getOutputName().equals("output0");
  }

  private FileUploadRequest testRequest() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final FileUploadRequest request = new FileUploadRequest(historyId, testFile);
    return request;
  }

}
