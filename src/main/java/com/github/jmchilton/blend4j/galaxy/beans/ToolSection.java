package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.ToolsClient;

/**
 * Very basic details about tools. For more information about a tool, load a
 * {@link ToolDetails} instance with {@link ToolsClient#showTool}.
 * 
 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToolSection extends GalaxyObject {
	private String version;

	private String name;
	
	private String description;
	
	private List<Tool> elems;
	
	@JsonProperty("description")
	protected void setDescription(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@JsonProperty("elems")
	protected void setElems(final List<Tool> elems) {
		this.elems = elems;
	}
	
	public List<Tool> getElems() {
		return this.elems;
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
