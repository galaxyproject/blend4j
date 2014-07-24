package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;

/**
 *  Class for storing response information from Galaxy on DatasetCollections within a History.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DatasetCollectionResponse extends HistoryContents implements ResponseObject {
  
  private List<CollectionResponse> elements;
  
  @JsonProperty("collection_type")
  private String collectionType;
  
  @JsonProperty("history_id")
  private String historyId;
  
  @JsonProperty("visible")
  private boolean visible;

  public List<CollectionResponse> getElements() {
    return elements;
  }

  public void setElements(List<CollectionResponse> elements) {
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

  @Override
  public int hashCode() {
    return Objects.hash(collectionType, elements, historyId, visible);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DatasetCollectionResponse) {
      DatasetCollectionResponse other = (DatasetCollectionResponse)obj;
      
      return Objects.equals(collectionType, other.collectionType) &&
          Objects.equals(elements, other.elements) && 
          Objects.equals(historyId, other.historyId) &&
          Objects.equals(visible, other.visible);
    }
    
    return false;
  }
}
