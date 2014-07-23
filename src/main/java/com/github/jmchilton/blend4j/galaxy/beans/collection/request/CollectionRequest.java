package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.CollectionSource;

/**
 * Description for a DatasetCollectionElement for requesting creation of a dataset collection in Galaxy.
 */
public abstract class CollectionRequest {
  
  @JsonProperty("name")
  private String name;
  
  @JsonProperty("src")
  private CollectionSource source;
  
  /**
   * Gets the source of this dataset element.
   * @return  The source of this dataset element.
   */
  public CollectionSource getSource() {
    return source;
  }

  /**
   * Sets the source of this dataset element.
   * @param source  The source of this dataset element.
   */
  public void setSource(CollectionSource source) {
    this.source = source;
  }

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
