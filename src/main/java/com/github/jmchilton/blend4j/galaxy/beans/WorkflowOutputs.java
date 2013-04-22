package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class WorkflowOutputs {
  private String historyId;
  private List<String> outputIds;

  public List<String> getOutputIds() {
    return outputIds;
  }

  @JsonProperty("outputs")
  public void seOutputIds(final List<String> outputIds) {
    this.outputIds = outputIds;
  }

  public String getHistoryId() {
    return historyId;
  }

  @JsonProperty("history")
  public void setHistoryId(final String historyId) {
    this.historyId = historyId;
  }
}
