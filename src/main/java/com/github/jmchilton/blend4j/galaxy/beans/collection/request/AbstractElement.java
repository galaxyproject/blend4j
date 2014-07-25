package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.CollectionSource;

/**
 * Description for elements used to create a dataset collection in Galaxy.
 */
public abstract class AbstractElement {
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("src")
  private CollectionSource source;
  
  /**
   * Gets the source of this element.
   * @return  The source of this element.
   */
  public CollectionSource getSource() {
    return source;
  }

  /**
   * Sets the source of this element.
   * @param source  The source of this element.
   */
  public void setSource(CollectionSource source) {
    this.source = source;
  }

  /**
   * Gets the name of this element.
   * @return The name of this element. 
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this element.
   * @param name  The name of this element.
   */
  public void setName(String name) {
    this.name = name;
  }
}
