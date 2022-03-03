package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * AMPPD extension
 * Bean for fields related to workflow step information, including details about input steps and tool inputs.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WorkflowStepDefinition {

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class WorkflowStepOutput {
		private String stepOutput;
		private Integer sourceStep;

		public String getStepOutput() {
			return stepOutput;
		}

		@JsonProperty("step_output")
		public void setStepOutput(String stepOutput) {
			this.stepOutput = stepOutput;
		}

		public Integer getSourceStep() {
			return sourceStep;
		}

		@JsonProperty("source_step")
		public void setSourceStep(Integer sourceStep) {
			this.sourceStep = sourceStep;
		}

	} 

	private String toolId;
	private String toolName;	// not included in original showWorkflow API but added for AMP
	private String toolVersion;
	private Integer id;
	private Map<String, WorkflowStepOutput> inputSteps;
	private Map<String, Object> toolInputs;
	private String type; // data_input or tool
	private String annotation;

	public String getAnnotation() {
		return annotation;
	}

	@JsonProperty("annotation")
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getToolId() {
		return toolId;
	}

	@JsonProperty("tool_id")
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolVersion() {
		return toolVersion;
	}

	@JsonProperty("tool_version")
	public void setToolVersion(String toolVersion) {
		this.toolVersion = toolVersion;
	}

	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, Object> getToolInputs() {
		return toolInputs;
	}

	@JsonProperty("tool_inputs")
	public void setToolInputs(final Map<String, Object> toolInputs) {
		this.toolInputs = toolInputs;

	}

	public Map<String, WorkflowStepOutput> getInputSteps() {
		return inputSteps;
	}

	@JsonProperty("input_steps")
	public void setInputSteps(final Map<String, WorkflowStepOutput> inputSteps) {
		this.inputSteps = inputSteps;
	}

	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

}
