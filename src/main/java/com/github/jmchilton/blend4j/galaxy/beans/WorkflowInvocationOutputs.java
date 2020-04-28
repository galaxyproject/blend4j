package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown=true)
public class WorkflowInvocationOutputs {
  private String historyId;
  private String id;
  private Map<String, WorkflowInvocationInputs.WorkflowInvocationInput> inputs = new HashMap<String, WorkflowInvocationInputs.WorkflowInvocationInput>();
  private String modelClass;
  private String state;
  private Map<String, WorkflowStepDefinition> steps;
  private String updateTime;
  private String uuid;
  private String workflowId;


  public String getHistoryId() {
    return historyId;
  }

  @JsonProperty("history_id")
  public void setHistoryId(final String historyId) {
    this.historyId = historyId;
  }

  public String getId() {return id;}

  @JsonProperty("id")
  public void setId(String id) {this.id = id;}

  public void setInput(final String inputName, final WorkflowInvocationInputs.WorkflowInvocationInput workflowInvocationInput) {
    this.inputs.put(inputName, workflowInvocationInput);
  }

  @JsonProperty("inputs")
  public Map<String, WorkflowInvocationInputs.WorkflowInvocationInput> getInputs() {
    return inputs;
  }

  public String getModelClass() {
    return modelClass;
  }

  @JsonProperty("model_class")
  public void setModelClass(final String modelClass) {
    this.modelClass = modelClass;
  }

  public String getState() {
    return state;
  }

  @JsonProperty("state")
  public void setState(final String state) {
    this.state = state;
  }

  @JsonProperty("steps")
  public Map<String, WorkflowStepDefinition> getSteps() {
    return steps;
  }

  public void setSteps(Map<String, WorkflowStepDefinition> steps) {
    this.steps = steps;
  }

  public String getUpdateTime() {return updateTime;}

  @JsonProperty("update_time")
  public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}

  public String getUuid() {return uuid;}

  @JsonProperty("uuid")
  public void setUuid(String uuid) {this.uuid = uuid;}

  public String getWorkflowId() {return workflowId;}

  @JsonProperty("workflow_id")
  public void setWorkflowId(String workflowId) {this.workflowId = workflowId;}
}
