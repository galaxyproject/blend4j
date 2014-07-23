package com.github.jmchilton.blend4j.galaxy.beans.dataset;

import org.codehaus.jackson.annotate.JsonProperty;

public abstract class SimpleElementDescription extends CollectionDescription {
  
  @JsonProperty("id")
  private String id;

  
  /**
   * Gets the id of this dataset element.
   * @return  The id of this dataset element.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of this dataset element.
   * @param id  The id of this dataset element.
   */
  public void setId(String id) {
    this.id = id;
  }
}
