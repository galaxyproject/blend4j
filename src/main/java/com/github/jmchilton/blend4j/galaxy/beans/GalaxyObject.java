package com.github.jmchilton.blend4j.galaxy.beans;

import com.github.jmchilton.blend4j.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * AMPPD extension
 * Base class for all beans.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class GalaxyObject {
	private String id;			
	private String uuid;		// uuid is globally unique within Galaxy
	private String url;			// not every bean has url
	@JsonProperty("model_class")
	private String modelClass;	// class of the invocation (this entity)

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonIgnore
	public String getUrl() {
		return url;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getModelClass() {
		return modelClass;
	}

	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}

	@Override
	public int hashCode() {
		// ID is not globally unique within Galaxy, but it's unique within the same model class. 
		return Objects.hashCode(id, modelClass);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof GalaxyObject) {
			GalaxyObject other = (GalaxyObject)obj;

			// ID is not globally unique within Galaxy, but it's unique within the same model class. 
			return Objects.equal(id, other.id) &&
					Objects.equal(modelClass, other.modelClass);
		}

		return false;
	}

}
