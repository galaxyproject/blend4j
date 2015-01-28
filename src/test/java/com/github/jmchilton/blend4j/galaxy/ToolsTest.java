package com.github.jmchilton.blend4j.galaxy;

import java.io.File;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.ToolsClient.FileUploadRequest;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.Tool;
import com.github.jmchilton.blend4j.galaxy.beans.ToolDetails;
import com.github.jmchilton.blend4j.galaxy.beans.ToolDetails.ToolDetailsGenomeBuildInputs;
import com.github.jmchilton.blend4j.galaxy.beans.ToolDetails.ToolDetailsInputs;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.github.jmchilton.blend4j.galaxy.beans.ToolSection;
import com.sun.jersey.api.client.ClientResponse;

public class ToolsTest {
  private GalaxyInstance instance;
  private ToolsClient client;
  
  private static final String UPLOADER_TOOL_ID = "upload1";
  
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
  
  /**
   * This test is just testing the complete workflow 
   * from loading all tools to getting an individual tool.
   */
  @Test
  public void testShowToolDetails() {
	  final List<ToolSection> tools = client.getTools();
	  final ToolSection toolSection = tools.iterator().next();
	  final Tool tool = toolSection.getElems().iterator().next();
	  final ToolDetails toolDetails = client.showTool(tool.getId());
	  
	  // confirm that we actually received something from the server
	  assert toolDetails != null;
  }
  
  /**
   * Test showing the details of a basic tool.
   */
  @Test
  public void testShowDefaultUploadTool() {
	  // we assume that the uploader tool comes stock with Galaxy.
	  final ToolDetails toolDetails = client.showTool(UPLOADER_TOOL_ID);
	  assert toolDetails != null;
	  assert UPLOADER_TOOL_ID.equals(toolDetails.getId());
	  // verify a couple of the inputs
	  final List<ToolDetailsInputs> inputs = toolDetails.getInputs();
	  assert inputs != null;
	  assert !inputs.isEmpty();
	  ToolDetailsGenomeBuildInputs genomeBuildInputs = null;
	  // find the "genome" input:
	  for (final ToolDetailsInputs input : inputs) {
		  if (input instanceof ToolDetailsGenomeBuildInputs) {
			  genomeBuildInputs = (ToolDetailsGenomeBuildInputs) input;
			  break;
		  }
	  }
	  
	  assert genomeBuildInputs != null;
  }
    
  private FileUploadRequest testRequest() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final FileUploadRequest request = new FileUploadRequest(historyId, testFile);
    return request;
  }

}
