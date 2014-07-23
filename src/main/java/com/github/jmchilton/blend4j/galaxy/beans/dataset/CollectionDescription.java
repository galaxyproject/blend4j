package com.github.jmchilton.blend4j.galaxy.beans.dataset;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Description for a DatasetCollectionElement for requesting creation of a dataset collection in Galaxy.
 */
public abstract class CollectionDescription {
  
  @JsonProperty("name")
  private String name;

  /**
   * Gets the name of this dataset element.
   * @return The name of this dataset element. 
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this dataset element.
   * @param name  The name of this dataset element.
   */
  public void setName(String name) {
    this.name = name;
  }
}
