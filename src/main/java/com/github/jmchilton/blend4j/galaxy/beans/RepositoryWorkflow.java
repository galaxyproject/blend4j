package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RepositoryWorkflow {
  private int index;
  private String name;
  private String annotation;
  @JsonProperty("format-version")
  private String formatVersion;

  public int getIndex() {
    return index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getAnnotation() {
    return annotation;
  }

  public void setAnnotation(final String annotation) {
    this.annotation = annotation;
  }

  public String getFormatVersion() {
    return formatVersion;
  }

  public void setFormatVersion(final String formatVersion) {
    this.formatVersion = formatVersion;
  }
  
}
