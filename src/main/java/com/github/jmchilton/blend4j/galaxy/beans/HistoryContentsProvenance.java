package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HistoryContentsProvenance {
  private String id;
  private String uuid;
  private String toolId;
  private Map<String, Object> parameters;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getToolId() {
    return toolId;
  }

  @JsonProperty("tool_id")
  public void setToolId(String toolId) {
    this.toolId = toolId;
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
  
  
  
}
