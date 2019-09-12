package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.Map;

public class WorkflowDetails extends Workflow {
  private Map<String, WorkflowInputDefinition> inputs;
  private Map<String, WorkflowStepDefinition> steps;
  private Map<ToolInputs, Tool> toolinputs;
	/*
	 * tags annotation model_class to be added
	 */
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
}
