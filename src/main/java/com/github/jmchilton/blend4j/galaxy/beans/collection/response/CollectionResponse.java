package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;

/**
 *  Class for storing response information from Galaxy on DatasetCollections within a History.
 */
@JsonIgnoreProperties(ignoreUnknown=false)
public class CollectionResponse extends HistoryContents {
  
  private List<CollectionElementResponse> elements;
  
  @JsonProperty("collection_type")
  private String collectionType;
  
  @JsonProperty("history_id")
  private String historyId;
  
  private boolean visible;

  public List<CollectionElementResponse> getElements() {
    return elements;
  }

  public void setElements(List<CollectionElementResponse> elements) {
    this.elements = elements;
  }

  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }

  public String getHistoryId() {
    return historyId;
  }

  public void setHistoryId(String historyId) {
    this.historyId = historyId;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
