package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;

/**
 * AMPPD extension
 * Bean for fields returned upon workflow show request, which includes detailed information on workflow inputs, steps, etc.
 */
public class WorkflowDetails extends Workflow {
	private Map<String, WorkflowInputDefinition> inputs;
	private Map<String, WorkflowStepDefinition> steps;
	private String annotation;
	private String version;

	public Map<String, WorkflowStepDefinition> getSteps() {
		return steps;
	}  

	public void setSteps(Map<String, WorkflowStepDefinition> steps) {
		this.steps = steps;
	}

	public void setInputs(final Map<String, WorkflowInputDefinition> inputs) {
		this.inputs = inputs;
	}

	public Map<String, WorkflowInputDefinition> getInputs() {
		return inputs;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
