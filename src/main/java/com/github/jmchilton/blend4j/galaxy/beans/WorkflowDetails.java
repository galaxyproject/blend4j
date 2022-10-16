package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;
import java.util.Map;

/**
 * AMPPD extension
 * Bean for fields returned upon workflow show request, which includes detailed information on workflow inputs, steps, etc.
 */
public class WorkflowDetails extends Workflow {
	// TODO The following fields are AMP specific, parsed from input steps, and better be moved to a subclass extended in AMP.
	// input node labels and formats (non AV data types) fed from workflow results for partial workflows;
	// in the same order as the input nodes, but excluding AV type inputs which would be from primaryfile
	private List<String> inputWprkflowResultLabels;	
	private List<String> inputWprkflowResultFormats;	 
	// index and format for the only AV type input node, fed from primaryfile; null if not applicable
	private Integer inputPrimaryfileIndex;	
	private String inputPrimaryfileFormat;
	// whether the workflow is a partial one that takes intermediate results as inputs
	private boolean partial;

	private Map<String, WorkflowInputDefinition> inputs;
	private Map<String, WorkflowStepDefinition> steps;

	// Note: Galaxy show workflow API includes annotation but not annotations, while the list workflows API includes annotations instead of annotation.
	private String annotation;
	private String license;
	private String version;

	
	public List<String> getInputWprkflowResultLabels() {
		return inputWprkflowResultLabels;
	}

	public void setInputWprkflowResultLabels(List<String> inputWprkflowResultLabels) {
		this.inputWprkflowResultLabels = inputWprkflowResultLabels;
	}

	public List<String> getInputWprkflowResultFormats() {
		return inputWprkflowResultFormats;
	}

	public void setInputWprkflowResultFormats(List<String> inputWprkflowResultFormats) {
		this.inputWprkflowResultFormats = inputWprkflowResultFormats;
	}

	public Integer getInputPrimaryfileIndex() {
		return inputPrimaryfileIndex;
	}

	public void setInputPrimaryfileIndex(Integer inputPrimaryfileIndex) {
		this.inputPrimaryfileIndex = inputPrimaryfileIndex;
	}

	public String getInputPrimaryfileFormat() {
		return inputPrimaryfileFormat;
	}

	public void setInputPrimaryfileFormat(String inputPrimaryfileFormat) {
		this.inputPrimaryfileFormat = inputPrimaryfileFormat;
	}

	public boolean isPartial() {
		return partial;
	}

	public void setPartial(boolean partial) {
		this.partial = partial;
	}

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
