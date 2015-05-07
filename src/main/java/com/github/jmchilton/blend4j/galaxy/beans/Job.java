package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job extends GalaxyObject {
	@JsonProperty("state")
	private String state;
	@JsonProperty("tool_id")
	private String toolId;
	@JsonProperty("create_time")
	private Date created;
	@JsonProperty("update_time")
	private Date updated;
	
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
	
	
}
