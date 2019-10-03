package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for fields included in an job (an execution of a tool, which could correspond to a step in a workflow invocation), without inputs/outputs details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job extends GalaxyObject {
	
	@JsonProperty("tool_id")
	private String toolId;
	@JsonProperty("update_time")
	private Date updated;
	@JsonProperty("exit_code")
	private Integer exitCode;
	@JsonProperty("state")
	private String state;
	@JsonProperty("create_time")
	private Date created;

	public final String getState() {
		return state;
	}
	public final void setState(String state) {
		this.state = state;
	}
	public final String getToolId() {
		return toolId;
	}
	public final void setToolId(String toolId) {
		this.toolId = toolId;
	}
	public final Date getCreated() {
		return created;
	}
	public final void setCreated(Date created) {
		this.created = created;
	}
	public final Date getUpdated() {
		return updated;
	}
	public final void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Integer getExitCode() {
		return exitCode;
	}
	public void setExitCode(Integer exitCode) {
		this.exitCode = exitCode;
	}

}
