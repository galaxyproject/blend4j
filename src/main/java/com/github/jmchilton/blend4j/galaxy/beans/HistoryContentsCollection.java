package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class HistoryContentsCollection extends HistoryContents {
  public String collectionType;

  public String getCollectionType() {
    return collectionType;
  }

  @JsonProperty("collection_type")
  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }

}
