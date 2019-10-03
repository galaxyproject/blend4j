package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for fields returned upon workflow index request, which does not include detailed information on workflow inputs, steps, etc.
 */
public class Workflow extends GalaxyObject {
	
	private String name;
	private List<String> tags = new ArrayList<String>();
	private boolean deleted;
	@JsonProperty("latest_workflow_uuid")
	private String latestWorkflowUuid;
	// omit showInToolPanel field as its not useful outside Galaxy UI
	@JsonProperty("number_of_steps")
	private Integer numberOfSteps;
	private boolean published;
	private String owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getLatestWorkflowUuid() {
		return latestWorkflowUuid;
	}

	public void setLatestWorkflowUuid(String latestWorkflowUuid) {
		this.latestWorkflowUuid = latestWorkflowUuid;
	}

	public Integer getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(Integer numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
