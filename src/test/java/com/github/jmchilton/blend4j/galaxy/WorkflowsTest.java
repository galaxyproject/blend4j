package com.github.jmchilton.blend4j.galaxy;

import static org.testng.AssertJUnit.*;

import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInvocationInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInvocationOutputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowStepDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkflowsTest {
  private static final String TEST_WORKFLOW_NAME = "TestWorkflow1";
  private static final String TEST_WORKFLOW_RANDOMLINES = "TestWorkflowRandomlines";
  private static final String TEST_WORKFLOW_COLLECTION_LIST = "TestWorkflowCollectionList";
  private GalaxyInstance instance;
  private WorkflowsClient client;
  
  private String testWorkflow1Contents;

  @BeforeMethod
  public void init() throws IOException {
    instance = TestGalaxyInstance.get();
    client = instance.getWorkflowsClient();
    
    testWorkflow1Contents = Resources.asCharSource(getClass().getResource(TEST_WORKFLOW_NAME + ".ga"), Charsets.UTF_8).read();
  }
  
  private String ensureHasTestWorkflow1() {
    return ensureHasWorkflow(TEST_WORKFLOW_NAME);
  }
  
  private String ensureHasWorkflow(final String workflowName) {
    String workflowId = null;
    for(Workflow workflow : client.getWorkflows()) {
      if(workflow.getName().startsWith(workflowName)) {
        workflowId = workflow.getId();
        break;
      }
    }
    if(workflowId == null) {
      final String workflowContents;
      try {
        workflowContents = Resources.asCharSource(getClass().getResource(workflowName + ".ga"), Charsets.UTF_8).read();
      } catch(IOException ex) {
        throw new RuntimeException(ex);
      }
      client.importWorkflow(workflowContents);
      workflowId = getTestWorkflowId(workflowName);
    }
    return workflowId;
  }
  
  /**
   * Constructs a list collection from the given files within the given history.
   * @param historyId  The id of the history to build the collection within.
   * @param inputIds  The ids of the files to add to the collection.
   * @return  A CollectionResponse object for the constructed collection.
   */
  private CollectionResponse constructFileCollectionList(String historyId, List<String> inputIds) {
    HistoriesClient historiesClient = instance.getHistoriesClient();
    
    CollectionDescription collectionDescription = new CollectionDescription();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName("collection");
    
    for (String inputId : inputIds) {
      HistoryDatasetElement element = new HistoryDatasetElement();
      element.setId(inputId);
      element.setName(inputId);
      
      collectionDescription.addDatasetElement(element);
    }
    
    return historiesClient.createDatasetCollection(historyId, collectionDescription);
  }
  
  /**
   * Prepares a workflow which takes as input a collection list.
   * @param inputSource  The type of input source for this workflow.
   * @return  A WorkflowInputs describing the workflow.
   * @throws InterruptedException
   */
  private WorkflowInputs prepareWorkflowCollectionList(WorkflowInputs.InputSourceType inputSource)
      throws InterruptedException {
    String historyId = TestHelpers.getTestHistoryId(instance);
    List<String> ids = TestHelpers.populateTestDatasets(instance, historyId, 2);
    String workflowId = ensureHasWorkflow(TEST_WORKFLOW_COLLECTION_LIST);
    
    CollectionResponse collectionResponse = constructFileCollectionList(historyId, ids);
    Assert.assertNotNull(collectionResponse.getId());
    
    WorkflowDetails workflowDetails = client.showWorkflow(workflowId);
    Assert.assertNotNull(workflowDetails.getId());
    
    String workflowInputId = getWorkflowInputId(workflowDetails, "input_list");
    Assert.assertNotNull(workflowInputId);
    
    WorkflowInputs workflowInputs = new WorkflowInputs();
    workflowInputs.setDestination(new WorkflowInputs.ExistingHistory(historyId));
    workflowInputs.setWorkflowId(workflowId);
    workflowInputs.setInput(workflowInputId,
        new WorkflowInputs.WorkflowInput(collectionResponse.getId(),
            inputSource));
    
    return workflowInputs;
  }

  /**
   * Tests execution of a workflow on a list collection of files and passing.
   * @throws InterruptedException 
   */
  @Test
  public void testRunWorkflowCollectionListPass() throws InterruptedException {
    WorkflowsClient workflowsClient = instance.getWorkflowsClient();
    
    WorkflowInputs workflowInputs = 
        prepareWorkflowCollectionList(WorkflowInputs.InputSourceType.HDCA);
    
    WorkflowOutputs outputs = workflowsClient.runWorkflow(workflowInputs);
    Assert.assertNotNull(outputs);
    Assert.assertNotNull(outputs.getOutputIds());
    Assert.assertEquals(1, outputs.getOutputIds().size());
    
    String outputId = outputs.getOutputIds().get(0);
    Assert.assertNotNull(outputId);
  }
  
  /**
   * Tests execution of a workflow on a list collection of files and failing.
   * @throws InterruptedException 
   */
  @Test(expectedExceptions=GalaxyResponseException.class)
  public void testRunWorkflowCollectionListFail() throws InterruptedException {
    WorkflowsClient workflowsClient = instance.getWorkflowsClient();
    
    WorkflowInputs workflowInputs = 
        prepareWorkflowCollectionList(WorkflowInputs.InputSourceType.HDA);
    
    workflowsClient.runWorkflow(workflowInputs);
  }
  
  @Test
  public void testExportWorkflow() {
    ensureHasTestWorkflow1();
    final String testWorkflowId = getTestWorkflowId();
    final String workflowExported = client.exportWorkflow(testWorkflowId);
    assert workflowExported.contains("a_galaxy_workflow");            
  }

  @Test
  public void testImportExportWorkflow() {
    ensureHasTestWorkflow1();
    final String testWorkflowId = getTestWorkflowId();
    final String workflowJson = client.exportWorkflow(testWorkflowId);
    final Workflow importedWorkflow = client.importWorkflow(workflowJson);
  }
  
  /**
   * Given a WorkflowDetails object, and a label for an input to a workflow, finds the id for this input.
   * @param workflowDetails  The WorkflowDetails object.
   * @param inputLabel  The label for the input to search for.
   * @return  The corresponding id for the passed label.
   */
  private String getWorkflowInputId(WorkflowDetails workflowDetails, String inputLabel) {
    String workflowInputId = null;

    for(final Map.Entry<String, WorkflowInputDefinition> inputEntry : workflowDetails.getInputs().entrySet()) {
      final String label = inputEntry.getValue().getLabel();
      if(label.equals(inputLabel)) {
        workflowInputId = inputEntry.getKey();
      }
    }

    return workflowInputId;
  }

  @Test
  public void testRunWorkflow() throws IOException, InterruptedException {
	ensureHasTestWorkflow1();
	  
    // Find history
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final List<String> ids = TestHelpers.populateTestDatasets(instance, historyId, 2);

    final String input1Id = ids.get(0);
    final String input2Id = ids.get(1);
 
    final String testWorkflowId = getTestWorkflowId();
    final WorkflowDetails workflowDetails = client.showWorkflow(testWorkflowId);
    String workflowInput1Id = getWorkflowInputId(workflowDetails, "WorkflowInput1");
    String workflowInput2Id = getWorkflowInputId(workflowDetails, "WorkflowInput2");

    final WorkflowInputs inputs = new WorkflowInputs();
    inputs.setDestination(new WorkflowInputs.ExistingHistory(historyId));
    inputs.setWorkflowId(testWorkflowId);
    inputs.setInput(workflowInput1Id, new WorkflowInputs.WorkflowInput(input1Id, WorkflowInputs.InputSourceType.HDA));
    inputs.setInput(workflowInput2Id, new WorkflowInputs.WorkflowInput(input2Id, WorkflowInputs.InputSourceType.HDA));
    final WorkflowOutputs output = client.runWorkflow(inputs);
    System.out.println("Running workflow in history " + output.getHistoryId());
    for(final String outputId : output.getOutputIds()) {
      System.out.println("  Workflow Output ID " + outputId);
    }
  }

  @Test
  public void testInvokeWorkflow() throws IOException, InterruptedException {
    ensureHasTestWorkflow1();

    // Find history
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final List<String> ids = TestHelpers.populateTestDatasets(instance, historyId, 2);

    final String input1Id = ids.get(0);
    final String input2Id = ids.get(1);

    final String testWorkflowId = getTestWorkflowId();
    final WorkflowDetails workflowDetails = client.showWorkflow(testWorkflowId);
    String workflowInput1Id = getWorkflowInputId(workflowDetails, "WorkflowInput1");
    String workflowInput2Id = getWorkflowInputId(workflowDetails, "WorkflowInput2");

    final WorkflowInvocationInputs inputs = new WorkflowInvocationInputs();
    inputs.setDestination(new WorkflowInvocationInputs.ExistingHistory(historyId));
    inputs.setWorkflowId(testWorkflowId);
    inputs.setInput(workflowInput1Id, new WorkflowInvocationInputs.WorkflowInvocationInput(input1Id, WorkflowInvocationInputs.InputSourceType.HDA));
    inputs.setInput(workflowInput2Id, new WorkflowInvocationInputs.WorkflowInvocationInput(input2Id, WorkflowInvocationInputs.InputSourceType.HDA));
    final WorkflowInvocationOutputs output = client.invokeWorkflow(inputs);
    System.out.println("Running workflow in history " + output.getHistoryId());
    final String workflowID = output.getWorkflowId();
    System.out.println("  Workflow ID " + workflowID);
  }

  @Test
  public void testWorkflowToolParameter() throws InterruptedException {
	ensureHasTestWorkflow1();
	
    final WorkflowInputs inputs = prepParameterTest();
    inputs.setToolParameter("random_lines1", "num_lines", 5);
    final WorkflowOutputs output = client.runWorkflow(inputs);
    // TODO: Verify outputs...
  }

  @Test
  public void testWorkflowStepParameter() throws InterruptedException {
	ensureHasTestWorkflow1();
	
    final WorkflowInputs inputs = prepParameterTest();

    final WorkflowDetails workflowDetails = client.showWorkflow(inputs.getWorkflowId());
    workflowDetails.getInputs();
    String firstStepId = null, secondStepId = null;
    for(final Map.Entry<String, WorkflowStepDefinition> entry : workflowDetails.getSteps().entrySet()) {
      final String stepId = entry.getKey();
      final WorkflowStepDefinition stepDef = entry.getValue();
      if(!stepDef.getType().equals("tool")) {
        continue;
      }
      boolean firstStep = true;
      for(final Map.Entry<String, WorkflowStepDefinition.WorkflowStepOutput> stepInput : stepDef.getInputSteps().entrySet()) {
        if(stepInput.getValue().getStepOutput().equals("out_file1")) {
          // Has an input from random lines tool, is second step...
          firstStep = false;
        }
      }
      if(firstStep) {
        firstStepId = stepId;
      } else {
        secondStepId = stepId;
      }
    }

    inputs.setStepParameter(firstStepId, "num_lines", 7);
    inputs.setStepParameter(secondStepId, "num_lines", 3);
    final WorkflowOutputs output = client.runWorkflow(inputs);
    // TODO: Verify outputs...
  }
  
  /**
   * Tests successfully deleting a workflow.
   */
  @Test
  public void deleteWorkflowRequestSuccess() {
    Workflow workflow = client.importWorkflow(testWorkflow1Contents);
    WorkflowDetails workflowDetails = client.showWorkflow(workflow.getId());
    assert !workflowDetails.isDeleted() : "Workflow is deleted";
    
    client.deleteWorkflowRequest(workflow.getId());
    
    workflowDetails = client.showWorkflow(workflow.getId());
    assert workflowDetails.isDeleted() : "Workflow is not deleted";
  }
  
  /**
   * Tests failing to deleting an invalid workflow.
   */
  @Test
  public void deleteWorkflowRequestFail() {
    Workflow workflow = client.importWorkflow(testWorkflow1Contents);
    
    try {
      client.showWorkflow("invalid");
      fail("The invalid workflow above exists");
    } catch (UniformInterfaceException e) {
      assert 400 == e.getResponse().getStatus() : "Invalid status code";
    }
    
    try {
      client.deleteWorkflowRequest(workflow.getId());
    } catch (UniformInterfaceException e) {
      assert 400 == e.getResponse().getStatus() : "Invalid status code";
    }
  }
  
  private WorkflowInputs prepParameterTest() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final String testContents = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10";
    final String datasetId = TestHelpers.populateTestDataset(this.instance, historyId, testContents);

    final String testWorkflowId = ensureHasWorkflow(TEST_WORKFLOW_RANDOMLINES);
    final WorkflowDetails workflowDetails = client.showWorkflow(testWorkflowId);
    final WorkflowInputs inputs = new WorkflowInputs();
    inputs.setDestination(new WorkflowInputs.ExistingHistory(historyId));
    inputs.setWorkflowId(testWorkflowId);
    final String inputId = workflowDetails.getInputs().keySet().iterator().next();
    inputs.setInput(inputId, new WorkflowInputs.WorkflowInput(datasetId, WorkflowInputs.InputSourceType.HDA));
    return inputs;
  }
  
  private String getTestWorkflowId() {
    return getTestWorkflowId(TEST_WORKFLOW_NAME);
  }
  
  private String getTestWorkflowId(final String name) {
    Workflow matchingWorkflow = null;
    for(Workflow workflow : client.getWorkflows()) {
      if(workflow.getName().startsWith(name)) {
        matchingWorkflow = workflow;
      }
    }
    return matchingWorkflow.getId();
  }
}