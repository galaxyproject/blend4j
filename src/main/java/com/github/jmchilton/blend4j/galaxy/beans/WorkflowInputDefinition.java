package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WorkflowInputDefinition {
  private String value;
  private String label;
  @JsonProperty("uuid")
  private String uuid;

  public void setValue(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public void setUuid(final String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }
}
