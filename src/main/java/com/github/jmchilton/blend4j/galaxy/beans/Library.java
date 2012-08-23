package com.github.jmchilton.blend4j.galaxy.beans;

public class Library extends GalaxyObject {
  private String name = "";
  private String description = "";
  private String synopsis = "";
  
  public Library() {
  }
  
  public Library(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

}