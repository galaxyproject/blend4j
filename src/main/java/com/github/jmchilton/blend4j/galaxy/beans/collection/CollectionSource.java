package com.github.jmchilton.blend4j.galaxy.beans.collection;

import org.codehaus.jackson.annotate.JsonValue;

/**
 * Describes the source of data for a dataset collection.
 */
public enum CollectionSource {
  
  /**
   * The dataset source is from a History.
   */
  HDA("hda"),
  
  /**
   * The dataset source is from a Library.
   */
  LIBRARY("lda"),
  
  /**
   * The dataset source is a new collection.
   */
  COLLECTION("new_collection");
  
  private String source;
  
  private CollectionSource(final String source) {
    this.source = source;
  }
  
  @JsonValue
  public String getSource() {
    return source;
  }
}
