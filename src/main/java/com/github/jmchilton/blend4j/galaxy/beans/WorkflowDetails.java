package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;
import java.util.Map;

/**
 * AMPPD extension
 * Bean for fields returned upon workflow show request, which includes detailed information on workflow inputs, steps, etc.
 */
public class WorkflowDetails extends Workflow {
	private Map<String, WorkflowInputDefinition> inputs;
	private Map<String, WorkflowStepDefinition> steps;

	// TODO The following fields are AMP specific and better be moved to a subclass extended in AMP repository.
	// input node labels and formats (data types) parsed from input node steps, fed from intermediate results for partial workflows;
	// they are in the same order as the input nodes, but excluding AV type inputs which would be from primaryfile
	private List<String> inputNonAvLabels;	
	private List<String> inputNonAvFormats;	 
	// index for the only AV type input node, fed from primaryfile; null if not applicable
	private Integer inputAvindex;	
		
	// Note: Galaxy show workflow API includes annotation but not annotations, while the list workflows API includes annotations instead of annotation.
	private String annotation;
	private String license;
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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
