package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;

public class WorkflowDetails extends Workflow {
  private boolean deleted;
  private boolean published;
  private String owner;
  private Map<String, WorkflowInputDefinition> inputs;
  private Map<String, WorkflowStepDefinition> steps;

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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
}
