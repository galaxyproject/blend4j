package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Base class for workflow invocation related fields shared by workflow outputs and invocation briefs/details query results, including inputs/outputs but not steps information.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvocationBase extends Invocation {

	private Map<String, WorkflowInput> inputs = new HashMap<String, WorkflowInput>();

	public Map<String, WorkflowInput> getInputs() {
		return inputs;
	}

	public void setInputs(Map<String, WorkflowInput> inputs) {
		this.inputs = inputs;
	}

	// we could have inherited from WorkflowInputs.WorkflowInput, except that "src" is of Enum InputSourceType, which uses upper case names, 
	// and that differs from the actual string values returned by Galaxy, so it can't be understood by json parser 
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class WorkflowInput {
	    private String id;
	    private String src;
		// WorkflowInput.WorkflowInput does not include uuid; but Galaxy does return it with invocation outputs
		// we need to include uuid otherwise json parser will throw exception
		private String uuid;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}
			
		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}		
	}	
	
}
