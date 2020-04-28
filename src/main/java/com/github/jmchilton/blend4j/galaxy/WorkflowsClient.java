package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInvocationInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInvocationOutputs;
import com.sun.jersey.api.client.ClientResponse;

public interface WorkflowsClient {
  List<Workflow> getWorkflows();

  WorkflowDetails showWorkflow(String workflowId);

  String exportWorkflow(String id);

  Workflow importWorkflow(String json);

  Workflow importWorkflow(String json, boolean publish);

  ClientResponse importWorkflowResponse(String json, final boolean publish);

  @Deprecated
  ClientResponse runWorkflowResponse(WorkflowInputs workflowInputs);

  @Deprecated
  WorkflowOutputs runWorkflow(WorkflowInputs workflowInputs);

  WorkflowInvocationOutputs invokeWorkflow(WorkflowInvocationInputs workflowInvocationInputs);
  
  /**
   * Deletes the workflow with the given id (this is irreversible). This will
   * return a {@link ClientResponse} object providing access to the status code
   * and the non-serialized body of the response.
   * 
   * @param id
   *          The id of the workflow to delete.
   * @return A {@link ClientResponse} for this request. The status code provided
   *         by {@link ClientResponse#getClientResponseStatus()} should be
   *         verified for success.
   */
  ClientResponse deleteWorkflowRequest(String id);
}
