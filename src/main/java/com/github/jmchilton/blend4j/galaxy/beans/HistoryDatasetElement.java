package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class HistoryDatasetElement {
  
  @JsonProperty("id")
  private String id;
  
  @JsonProperty("src")
  private String source;
  
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

  /**
   * Gets the source of this dataset element.
   * @return  The source of this dataset element.
   */
  public String getSource() {
    return source;
  }

  /**
   * Sets the source of this dataset element.
   * @param source  The source of this dataset element.
   */
  public void setSource(String source) {
    this.source = source;
  }
}
