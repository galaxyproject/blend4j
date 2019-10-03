package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * AMPPD extension
 * Bean for fields related to a workflow invocation, including inputs and basic information of each invocation step without details about jobs/outputs. 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvocationBriefs extends InvocationBase {

	private List<InvocationStep> steps = new ArrayList<InvocationStep>();
	
	public List<InvocationStep> getSteps() {
		return steps;
	}

	public void setSteps(List<InvocationStep> steps) {
		this.steps = steps;
	}

}
