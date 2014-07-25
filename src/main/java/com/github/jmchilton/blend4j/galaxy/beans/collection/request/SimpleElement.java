package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Class describing requests for the creation of a simple element in a dataset (no sub elements).
 */
public abstract class SimpleElement extends AbstractElement {
  
  @JsonProperty("id")
  private String id;
  
  /**
   * Gets the id of this element.
   * @return  The id of this element.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of this element.
   * @param id  The id of this element.
   */
  public void setId(String id) {
    this.id = id;
  }
}
