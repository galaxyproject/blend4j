package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.ExistingHistory;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.InputSourceType;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.WorkflowInput;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowStepDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkflowsTest {
  private static final String TEST_WORKFLOW_NAME = "TestWorkflow1";
  private static final String TEST_WORKFLOW_RANDOMLINES = "TestWorkflowRandomlines";
  private GalaxyInstance instance;
  private WorkflowsClient client;

  @BeforeMethod
  public void init() throws URISyntaxException, IOException {
    //TestGalaxyInstance.bootStrapGalaxy();
    instance = TestGalaxyInstance.get();
    client = instance.getWorkflowsClient();
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
   * Tests execution of a workflow on a list collection of files and passing.
   * @throws InterruptedException 
   */
  @Test
  public void testRunWorkflowCollectionListPass() throws InterruptedException {
    WorkflowsClient workflowsClient = instance.getWorkflowsClient();
    
    String historyId = TestHelpers.getTestHistoryId(instance);
    List<String> ids = TestHelpers.populateTestDatasets(instance, historyId, 2);
    String workflowId = ensureHasWorkflow("TestWorkflowCollectionList");
    
    CollectionResponse collectionResponse = constructFileCollectionList(historyId, ids);
    Assert.assertNotNull(collectionResponse.getId());
    
    WorkflowDetails workflowDetails = client.showWorkflow(workflowId);
    Assert.assertNotNull(workflowDetails.getId());
    
    String workflowInputId = getWorkflowInputId(workflowDetails, "input_label");
    Assert.assertNotNull(workflowInputId);
    
    WorkflowInputs workflowInputs = new WorkflowInputs();
    workflowInputs.setDestination(new WorkflowInputs.ExistingHistory(historyId));
    workflowInputs.setWorkflowId(workflowId);
    workflowInputs.setInput(workflowId, 
        new WorkflowInputs.WorkflowInput(collectionResponse.getId(),
            WorkflowInputs.InputSourceType.HDCA));
    
    WorkflowOutputs outputs = workflowsClient.runWorkflow(workflowInputs);
    Assert.assertNotNull(outputs);
    Assert.assertNotNull(outputs.getOutputIds());
    Assert.assertEquals(1, outputs.getOutputIds().size());
    
    String outputId = outputs.getOutputIds().get(0); 
    Assert.assertNotNull(outputId);
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
    inputs.setDestination(new ExistingHistory(historyId));
    inputs.setWorkflowId(testWorkflowId);
    inputs.setInput(workflowInput1Id, new WorkflowInput(input1Id, InputSourceType.HDA));
    inputs.setInput(workflowInput2Id, new WorkflowInput(input2Id, InputSourceType.HDA));
    final WorkflowOutputs output = client.runWorkflow(inputs);
    System.out.println("Running workflow in history " + output.getHistoryId());
    for(final String outputId : output.getOutputIds()) {
      System.out.println("  Workflow Output ID " + outputId);
    }
  }

  @Test
  public void testWorkflowToolParameter() throws InterruptedException {
    final WorkflowInputs inputs = prepParameterTest();
    inputs.setToolParameter("random_lines1", "num_lines", 5);
    final WorkflowOutputs output = client.runWorkflow(inputs);
    // TODO: Verify outputs...
  }

  @Test
  public void testWorkflowStepParameter() throws InterruptedException {
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
  

  
  private WorkflowInputs prepParameterTest() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final String testContents = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10";
    final String datasetId = TestHelpers.populateTestDataset(this.instance, historyId, testContents);

    final String testWorkflowId = ensureHasWorkflow(TEST_WORKFLOW_RANDOMLINES);
    final WorkflowDetails workflowDetails = client.showWorkflow(testWorkflowId);
    final WorkflowInputs inputs = new WorkflowInputs();
    inputs.setDestination(new ExistingHistory(historyId));
    inputs.setWorkflowId(testWorkflowId);
    final String inputId = workflowDetails.getInputs().keySet().iterator().next();
    inputs.setInput(inputId, new WorkflowInput(datasetId, InputSourceType.HDA));
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