package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for fields returned upon workflow index request, which does not include detailed information on workflow inputs, steps, etc.
 */
public class Workflow extends GalaxyObject {
	
	private String name;
	private String owner;

	private boolean published;
	private boolean deleted;
	private boolean hidden;	
	@JsonProperty("show_in_tool_panel")
	private boolean showInToolPanel;

	// Note: Galaxy list workflows API includes annotations instead of annotation, while the show workflow API includes annotation but not annotations.
	private List<String> annotations = new ArrayList<String>();
	private List<String> tags = new ArrayList<String>();
	
	@JsonProperty("create_time")
	private Date createTime;
	@JsonProperty("update_time")
	private Date updateTime;
	
	@JsonProperty("latest_workflow_uuid")
	private String latestWorkflowUuid;
	
	@JsonProperty("number_of_steps")
	private Integer numberOfSteps;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isShowInToolPanel() {
		return showInToolPanel;
	}

	public void setShowInToolPanel(boolean showInToolPanel) {
		this.showInToolPanel = showInToolPanel;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	
}
