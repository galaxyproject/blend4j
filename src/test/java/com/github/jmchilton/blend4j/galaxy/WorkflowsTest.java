package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.ExistingHistory;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.InputSourceType;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.WorkflowInput;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowStepDefinition;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkflowsTest {
  private static final String TEST_WORKFLOW_NAME = "TestWorkflow1";
  private static final String TEST_WORKFLOW_RANDOMLINES = "TestWorkflowRandomlines";
  private GalaxyInstance instance;
  private WorkflowsClient client;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    client = instance.getWorkflowsClient();
  }
  
  private String ensureHasTestWorklfow1() {
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
  
  @Test
  public void testExportWorkflow() {
    ensureHasTestWorklfow1();
    final String testWorkflowId = getTestWorkflowId();
    final String workflowExported = client.exportWorkflow(testWorkflowId);
    assert workflowExported.contains("a_galaxy_workflow");            
  }

  @Test
  public void testImportExportWorkflow() {
    ensureHasTestWorklfow1();
    final String testWorkflowId = getTestWorkflowId();
    final String workflowJson = client.exportWorkflow(testWorkflowId);
    final Workflow importedWorkflow = client.importWorkflow(workflowJson);
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
    String workflowInput1Id = null;
    String workflowInput2Id = null;
    for(final Map.Entry<String, WorkflowInputDefinition> inputEntry : workflowDetails.getInputs().entrySet()) {
      final String label = inputEntry.getValue().getLabel();
      if(label.equals("WorkflowInput1")) {
        workflowInput1Id = inputEntry.getKey();
      }
      if(label.equals("WorkflowInput2")) {
        workflowInput2Id = inputEntry.getKey();
      }
    }

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