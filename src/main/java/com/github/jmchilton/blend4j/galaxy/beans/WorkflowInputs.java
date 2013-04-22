package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.HashMap;
import java.util.Map;

public class WorkflowInputs {
  // Why are we posting to /workflows and putting this in the payload instead
  // of posting to /workflows/<workflow_id>. Seems incongruous/unrestful.
  private String workflowId;
  private WorkflowDestination destination;
  private boolean importInputsToHistory = false;
  private Map<String, WorkflowInput> inputs = new HashMap<String, WorkflowInput>();
  private Map<String, ToolParameter> parameters = new HashMap<String, ToolParameter>();

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

  public void setInput(final String inputName, final WorkflowInput workflowInput) {
    this.inputs.put(inputName, workflowInput);
  }

  @JsonProperty("ds_map")
  public Map<String, WorkflowInput> getInputs() {
    return inputs;
  }

  public void setParameter(final String toolName, final ToolParameter toolParameter) {
    this.parameters.put(toolName, toolParameter);
  }

  @JsonProperty("parameters")
  @JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
  public Map<String, ToolParameter> getParameters() {
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

  public static class WorkflowInput {
    private String id;
    private InputSourceType inputSourceType;

    public WorkflowInput(final String id, final InputSourceType inputSourceType) {
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

  public enum InputSourceType {
    LDDA("ldda"), LD("ld"), HDA("hda");
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
