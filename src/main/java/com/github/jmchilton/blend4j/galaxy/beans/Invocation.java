package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for basic information related to workflow invocation, which corresponds to the results returned by Galaxy API upon indexing invocations.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Invocation extends GalaxyObject {
	
	@JsonProperty("update_time")
	private Date updateTime;
	
	// in response from workflow invocation, there is also a field "history", which is the same as historyId, but its not present in workflow invocation query
	@JsonProperty("history_id")
	private String historyId;	
	
	private String state;
	
	// we omit the output_collection, since we don't use collection
	
	@JsonProperty("workflow_id")
	private String workflowId;	// an internal workflow ID, not the same as the API workflowId used for workflow query/invocation
	
	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(final String historyId) {
		this.historyId = historyId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}	
	
}
