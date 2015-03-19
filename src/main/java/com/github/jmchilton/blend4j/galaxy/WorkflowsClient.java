package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.sun.jersey.api.client.ClientResponse;

public interface WorkflowsClient {
  List<Workflow> getWorkflows();

  WorkflowDetails showWorkflow(String workflowId);

  String exportWorkflow(String id);

  Workflow importWorkflow(String json);

  ClientResponse importWorkflowResponse(String json);

  ClientResponse runWorkflowResponse(WorkflowInputs workflowInputs);

  WorkflowOutputs runWorkflow(WorkflowInputs workflowInputs);
  
  /**
   * Deletes the workflow with the given id.  This action is irreversible.
   * @param id The id of the workflow to delete.
   * @return A {@link ClientResponse} for this request.
   */
  ClientResponse deleteWorkflowResponse(String id);
}
