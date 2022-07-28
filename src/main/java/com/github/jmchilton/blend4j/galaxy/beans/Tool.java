package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for fields returned upon show tool detail request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tool extends GalaxyObject {
	private String version;

	private String description;

	private String name;
	
	private String link;
	
	private String target;
	
	private boolean hidden;
	
	@JsonProperty("panel_section_id")
	private String sectionId;
	
	@JsonProperty("panel_section_name")
	private String sectionName;
	
	private List<String> labels = new ArrayList<String>();
	
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	@JsonProperty("link")
	protected void setLink(final String link) {
		this.link = link;
	}
	
	public String getLink() {
		return this.link;
	}
	
	@JsonProperty("target")
	protected void setTarget(final String target) {
		this.target = target;
	}
	
	public String getTarget() {
		return this.target;
	}

	@JsonProperty("description")
	protected void setDescription(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	@JsonProperty("version")
	protected void setVersion(final String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

	@JsonProperty("name")
	protected void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
