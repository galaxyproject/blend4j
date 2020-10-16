package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Invocation;
import com.github.jmchilton.blend4j.galaxy.beans.InvocationBase;
import com.github.jmchilton.blend4j.galaxy.beans.InvocationBriefs;
import com.github.jmchilton.blend4j.galaxy.beans.InvocationDetails;
import com.github.jmchilton.blend4j.galaxy.beans.InvocationStepDetails;
import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowDetails;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowInputs;
import com.github.jmchilton.blend4j.galaxy.beans.WorkflowOutputs;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * AMPPD extension
 * Implementation for workflows and invocations.
 */
class WorkflowsClientImpl extends Client implements WorkflowsClient {
	public WorkflowsClientImpl(GalaxyInstanceImpl galaxyInstance) {
		super(galaxyInstance, "workflows");
	}

	public List<Workflow> getWorkflows() {
		return get(new TypeReference<List<Workflow>>() {
		});
	}

	public ClientResponse showWorkflowResponse(final String id) {
		return super.show(id, ClientResponse.class);
	}

	@Override
	public WorkflowDetails showWorkflow(final String id) {
		return super.show(id, WorkflowDetails.class);
	}

	@Override
	public WorkflowDetails showWorkflowInstance(String workflowId) {
		// adding query parameter instance=true to fetch by non-StoredWorkflow id instead of StoredWorkflow id 
		return getWebResource(workflowId).queryParam("instance",  "true").get(WorkflowDetails.class);
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

	@Override
	public ClientResponse deleteWorkflowRequest(String id) {
		return deleteResponse(getWebResource(id));
	}

	/**
	 * Returns the WebResource for invocations (excluding workflows in the path).
	 * @return
	 */
	protected WebResource getInvocationWebResource() {
		return this.getGalaxyInstance().getWebResource().path("invocations");
	}

	/**
	 * Returns the WebResource for invocations of the specified workflow.
	 * @param workflowId ID of the specified workflow.
	 * @return
	 */
	protected WebResource getInvocationWebResource(String workflowId) {
		return getWebResource().path(workflowId).path("invocations");
	}

	/**
	 * Returns the WebResource for the specified invocation of the specified workflow.
	 * @param workflowId ID of the specified workflow.
	 * @param invocationId ID of the specified invocation.
	 * @return
	 */
	protected WebResource getInvocationWebResource(String workflowId, String invocationId) {
		return getWebResource().path(workflowId).path("invocations").path(invocationId);
	}

	/**
	 * Returns the WebResource for the specified step of the specified invocation of the specified workflow.
	 * @param workflowId ID of the specified workflow.
	 * @param invocationId ID of the specified invocation.
	 * @param stepId ID of the specified step.
	 * @return
	 */
	protected WebResource getInvocationWebResource(String workflowId, String invocationId, String stepId) {
		return getWebResource().path(workflowId).path("invocations").path(invocationId).path("steps").path(stepId);
	}

	@Override
	public List<InvocationDetails> indexInvocationsDetails(String username) {
		WebResource webResource = getInvocationWebResource().queryParam("view", "element").queryParam("step_details", "true");
		if (username != null && !username.isEmpty()) {
			webResource = webResource.queryParam("user_id",  username);
		}
		return get(webResource, new TypeReference<List<InvocationDetails>>() {});
	}

	@Override
	public List<InvocationDetails> indexInvocationsDetails(String username, String workflowId, String historyId) {
		WebResource webResource = getInvocationWebResource().queryParam("view", "element").queryParam("step_details", "true");
		if (username != null && !username.isEmpty()) {
			webResource = webResource.queryParam("user_id",  username);
		}
		if (workflowId != null && !workflowId.isEmpty()) {
			webResource = webResource.queryParam("workflow_id",  workflowId);
		}
		if (historyId != null && !historyId.isEmpty()) {
			webResource = webResource.queryParam("history_id",  historyId);
		}
		return get(webResource, new TypeReference<List<InvocationDetails>>() {});
	}

	@Override
	public List<Invocation> indexInvocations(String workflowId, String historyId) {
		return get(getInvocationWebResource(workflowId).queryParam("history_id",  historyId), new TypeReference<List<Invocation>>() {});
	}

	@Override
	public InvocationBase showInvocation(String workflowId, String invocationId, Boolean detailed) {
		if (detailed) {
			return getInvocationWebResource(workflowId, invocationId).queryParam("step_details", "true").get(InvocationDetails.class);
		}
		else {
			return getInvocationWebResource(workflowId, invocationId).get(InvocationBriefs.class);
		}
	}

	@Override
	public InvocationStepDetails showInvocationStep(String workflowId, String invocationId, String stepId) {
		return getInvocationWebResource(workflowId, invocationId, stepId).get(InvocationStepDetails.class);	  
	}

}
