package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *  Class for storing information on DatasetCollections within a History.
 */
@JsonIgnoreProperties(ignoreUnknown=false)
public class DatasetCollection extends HistoryContents {
  
  private List<DatasetCollectionElement> elements;
  
  @JsonProperty("collection_type")
  private String collectionType;
  
  @JsonProperty("history_id")
  private String historyId;
  
  private boolean visible;

  public List<DatasetCollectionElement> getElements() {
    return elements;
  }

  public void setElements(List<DatasetCollectionElement> elements) {
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
