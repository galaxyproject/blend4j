package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputDefinition;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.ExistingHistory;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.InputSourceType;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs.WorkflowInput;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WorkflowsTest {
  private static final String TEST_WORKFLOW_NAME = "TestWorkflow1";
  private GalaxyInstance instance;
  private WorkflowsClient client;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    client = instance.getWorkflowsClient();
  }
  
  private void ensureHasTestWorklfow1() {
    boolean found = false;
    for(Workflow workflow : client.getWorkflows()) {
      if(workflow.getName().equals(TEST_WORKFLOW_NAME)) {
        found = true;
        break;
      }
    }
    if(!found) {
      final String workflowContents;
      try {
        workflowContents = Resources.asCharSource(getClass().getResource(TEST_WORKFLOW_NAME + ".ga"), Charsets.UTF_8).read();
      } catch(IOException ex) {
        throw new RuntimeException(ex);
      }
      client.importWorkflow(workflowContents);
    }
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

  //@Test
  public void testRunWorkflow() throws IOException {
    // Find history
    final HistoriesClient historyClient = instance.getHistoriesClient();
    History matchingHistory = null;
    for(final History history : historyClient.getHistories()) {
      if(history.getName().equals("TestHistory1")) {
        matchingHistory = history;
      }
    }
    Assert.assertNotNull(matchingHistory);
    String input1Id = null;
    String input2Id = null;
    for(final HistoryContents historyDataset : historyClient.showHistoryContents(matchingHistory.getId())) {
      if(historyDataset.getName().equals("Input1")) {
        input1Id = historyDataset.getId();
      }
      if(historyDataset.getName().equals("Input2")) {
        input2Id = historyDataset.getId();
      }
    }
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
    inputs.setDestination(new ExistingHistory(matchingHistory.getId()));
    inputs.setWorkflowId(testWorkflowId);
    inputs.setInput(workflowInput1Id, new WorkflowInput(input1Id, InputSourceType.HDA));
    inputs.setInput(workflowInput2Id, new WorkflowInput(input2Id, InputSourceType.HDA));
    final WorkflowOutputs output = client.runWorkflow(inputs);
    System.out.println("Running workflow in history " + output.getHistoryId());
    for(final String outputId : output.getOutputIds()) {
      System.out.println("  Workflow Output ID " + outputId);
    }
  }

  private String getTestWorkflowId() {
    Workflow matchingWorkflow = null;
    for(Workflow workflow : client.getWorkflows()) {
      if(workflow.getName().equals(TEST_WORKFLOW_NAME)) {
        matchingWorkflow = workflow;
      }
    }
    return matchingWorkflow.getId();
  }
}
