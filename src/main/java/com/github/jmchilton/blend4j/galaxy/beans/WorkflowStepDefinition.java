package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class WorkflowStepDefinition {
  
  @JsonIgnoreProperties(ignoreUnknown=true)
  public static class WorkflowStepOutput {
    private String stepOutput;
    private String sourceStep;

    public String getStepOutput() {
      return stepOutput;
    }

    @JsonProperty("step_output")
    public void setStepOutput(String stepOutput) {
      this.stepOutput = stepOutput;
    }

    public String getSourceStep() {
      return sourceStep;
    }

    @JsonProperty("source_step")
    public void setSourceStep(String sourceStep) {
      this.sourceStep = sourceStep;
    }
  
  }

  private Map<String, WorkflowStepOutput> inputSteps;
  private String type; // data_input or tool
  
  @JsonProperty("input_steps")
  public void setInputSteps(final Map<String, WorkflowStepOutput> inputSteps) {
    this.inputSteps = inputSteps;
  }

  public Map<String, WorkflowStepOutput> getInputSteps() {
    return this.inputSteps;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
    
}
