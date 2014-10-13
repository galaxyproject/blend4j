package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ToolInputs {
  private String toolId;
  private String historyId;
  private Map<String, Object> inputs;

  public ToolInputs(String toolId, Map<String, Object> inputs) {
    this.toolId = toolId;
    this.inputs = inputs;
  }

  public void setHistoryId(String historyId) {
    this.historyId = historyId;
  }

  @JsonProperty("tool_id")
  public String getToolId() {
    return toolId;
  }

  @JsonProperty("inputs")
  public Map<String, Object> getInputs() {
    return inputs;
  }

  @JsonProperty("history_id")
  public String getHistoryId() {
    return historyId;
  }
}
