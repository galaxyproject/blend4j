package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInvocationInputs {

  private String workflowId;
  private WorkflowDestination destination;
  private boolean importInputsToHistory = false;
  private Map<String, WorkflowInvocationInput> inputs = new HashMap<String, WorkflowInvocationInput>();
  private Map<Object, Map<String, Object>> parameters = new HashMap<Object, Map<String, Object>>();
  private boolean allowToolStateCorrections = false;
  private String inputsBy;

  public void setWorkflowId(final String workflowId) {
    this.workflowId = workflowId;
  }

  @JsonProperty("workflow_id")
  public String getWorkflowId() {
    return workflowId;
  }

  public void setDestination(final WorkflowDestination destination) {
    this.destination = destination;
  }

  @JsonProperty("history")
  public WorkflowDestination getDestination() {
    return destination;
  }

  public void setInput(final String inputName, final WorkflowInvocationInput workflowInvocationInput) {
    this.inputs.put(inputName, workflowInvocationInput);
  }

  @JsonProperty("inputs_by")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  public String getInputsBy() {
    return inputsBy;
  }

  public void setInputsBy(final String inputsBy) {
    this.inputsBy = inputsBy;
  }

  @JsonProperty("inputs")
  public Map<String, WorkflowInvocationInput> getInputs() {
    return inputs;
  }

  @Deprecated
  public void setParameter(final String toolId, final ToolParameter toolParameter) {
    this.setToolParameter(toolId, toolParameter);
  }

  public void setToolParameter(final String toolId, final String parameterName, final Object parameterValue) {
    this.setParameter(toolId, parameterName, parameterValue);
  }

  public void setToolParameter(final String toolId, final ToolParameter toolParameter) {
    this.setParameter(toolId, toolParameter.getParameterName(), toolParameter.getParameterValue());
  }

  // Some parts of API return step ids as strings others as ints - allow either
  public void setStepParameter(final String stepId, final String parameterName, final Object parameterValue) {
    this.setParameter(stepId, parameterName, parameterValue);
  }
  
  public void setStepParameter(final int stepId, final String parameterName, final Object parameterValue) {
    this.setParameter(stepId, parameterName, parameterValue);
  }

  public void setStepParameter(final String stepId, final ToolParameter toolParameter) {
    this.setParameter(stepId, toolParameter.getParameterName(), toolParameter.getParameterValue());
  }
  
  public void setStepParameter(final int stepId, final ToolParameter toolParameter) {
    this.setParameter(stepId, toolParameter.getParameterName(), toolParameter.getParameterValue());
  }

  synchronized private void setParameter(final Object toolOrStepId, final String parameterName, final Object parameterValue) {
    Map<String, Object> keyValueMap = this.parameters.get(toolOrStepId);
    if(keyValueMap == null) {
      keyValueMap = new HashMap<String, Object>();
    }
    keyValueMap.put(parameterName, parameterValue);
    this.parameters.put(toolOrStepId, keyValueMap);
  }
  
  @JsonProperty("parameters")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
  public Map<Object, Map<String, Object>> getParameters() {
    return parameters;
  }

  @JsonProperty("no_add_to_history")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
  public String getNoAddToHistory() {
    return importInputsToHistory ? null : "true";
  }

  @JsonIgnore
  public boolean getImportInputsToHistory() {
    return importInputsToHistory;
  }

  public void setImportInputsToHistory(final boolean importInputsToHistory) {
    this.importInputsToHistory = importInputsToHistory;
  }

  @JsonIgnore
  public boolean getAllowToolStateCorrections() {return allowToolStateCorrections; }

  public void setAllowToolStateCorrections(final boolean allowToolStateCorrections) {
    this.allowToolStateCorrections = allowToolStateCorrections;
  }

  public static class WorkflowDestination {
  }

  public static class NewHistory extends WorkflowDestination {
    private String name;

    public NewHistory(final String name) {
      this.name = name;
    }

    @JsonValue
    public String value() {
      return name;
    }
  }

  public static class ExistingHistory extends WorkflowDestination {
    private String id;

    public ExistingHistory(final String id) {
      this.id = id;
    }

    @JsonValue
    public String value() {
      return String.format("hist_id=%s", id);
    }
  }

  public static class WorkflowInvocationInput {
    private String id;
    private InputSourceType inputSourceType;

    public WorkflowInvocationInput(final String id, final InputSourceType inputSourceType) {
      this.id = id;
      this.inputSourceType = inputSourceType;
    }

    public String getId() {
      return id;
    }

    @JsonProperty("src")
    public InputSourceType getSourceType() {
      return inputSourceType;
    }
  }

  /**
   * Defines the different Workflow Input source types.
   */
  public enum InputSourceType {

    /**
     * Library dataset dataset association
     */
    LDDA("ldda"),

    /**
     * Library dataset
     */
    LD("ld"),

    /**
     * History dataset association
     */
    HDA("hda"),
    
    /**
     * History dataset collection association
     */
    HDCA("hdca");

    private final String rawValue;

    private InputSourceType(final String rawValue) {
      this.rawValue = rawValue;
    }

    @JsonValue
    public String value() {
      return rawValue;
    }
  }
}
