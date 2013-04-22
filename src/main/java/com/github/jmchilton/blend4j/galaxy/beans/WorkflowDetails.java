package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;

public class WorkflowDetails extends Workflow {
  private Map<String, WorkflowInputDefinition> inputs;

  public void setInputs(final Map<String, WorkflowInputDefinition> inputs) {
    this.inputs = inputs;
  }

  public Map<String, WorkflowInputDefinition> getInputs() {
    return inputs;
  }
}
