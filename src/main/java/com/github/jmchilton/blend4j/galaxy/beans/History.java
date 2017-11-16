package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class History extends GalaxyObject {
  private String name;
  boolean deleted;

  public History() {
  }

  public History(final String name) {
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public boolean isDeleted() {
	return deleted;
  }
  
  public void setDeleted(boolean deleted) {
	this.deleted = deleted;
  }
}
