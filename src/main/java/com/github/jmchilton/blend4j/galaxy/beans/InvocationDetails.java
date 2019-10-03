package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * AMPPD extension
 * Bean for fields related to a workflow invocation, including inputs and the detailed information such as jobs/outputs of each invocation step. 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvocationDetails extends InvocationBase {

	private List<InvocationStepDetails> steps = new ArrayList<InvocationStepDetails>();

	public List<InvocationStepDetails> getSteps() {
		return steps;
	}

	public void setSteps(List<InvocationStepDetails> steps) {
		this.steps = steps;
	}

}
