package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tool extends GalaxyObject {
	private String version;

	private String description;

	private String name;
	
	private String link;
	
	private String target;
	
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
