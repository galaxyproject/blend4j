package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

class WorkflowsClientImpl extends ClientImpl implements WorkflowsClient {

  public WorkflowsClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "workflows");
  }

  public List<Workflow> getWorkflows() {
    return get(new TypeReference<List<Workflow>>(){});
  }
  
  public ClientResponse showWorkflowResponse(final String id) {
    return super.show(id, ClientResponse.class);
  }

  public WorkflowDetails showWorkflow(final String id) {
    return super.show(id, WorkflowDetails.class);
  }
  
  public String exportWorkflow(final String id) {
    WebResource webResource = getWebResource().path("download").path(id);
    return webResource.get(String.class);
  }

  public ClientResponse runWorkflowResponse(WorkflowInputs workflowInputs) {
    return super.create(workflowInputs);
  }
  
  public WorkflowOutputs runWorkflow(final WorkflowInputs workflowInputs) {
    return runWorkflowResponse(workflowInputs).getEntity(WorkflowOutputs.class);
  }
  
  public ClientResponse importWorkflowResponse(final String json) {
    final String payload = String.format("{\"workflow\": %s}", json);
    return create(getWebResource().path("upload"), payload);
  }

  public Workflow importWorkflow(String json) {
    return importWorkflowResponse(json).getEntity(Workflow.class);
  }
  
}
