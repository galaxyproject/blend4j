package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import java.util.List;
import com.github.jmchilton.blend4j.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;

/**
 *  Class for storing response information from Galaxy on a dataset collection within a History.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CollectionResponse extends HistoryContents implements ElementResponse {
  
  @JsonProperty("elements")
  private List<CollectionElementResponse> elements;
  
  @JsonProperty("collection_type")
  private String collectionType;
  
  @JsonProperty("history_id")
  private String historyId;
  
  @JsonProperty("visible")
  private boolean visible;

  /**
   * Gets a list of elements within this collection.
   * @return  A list of elements within this collection.
   */
  public List<CollectionElementResponse> getElements() {
    return elements;
  }

  /**
   * Sets the list of elements within this collection.
   * @param elements  A list of elements within this collection.
   */
  public void setElements(List<CollectionElementResponse> elements) {
    this.elements = elements;
  }

  /**
   * Gets the type of this collection.
   * @return  The type fo this collection.
   */
  public String getCollectionType() {
    return collectionType;
  }

  /**
   * Sets the type of this collection.
   * @param collectionType  The type of this collection.
   */
  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }

  /**
   * Gets the id of the history this collection exists within.
   * @return  The id of the history this collection exists within.
   */
  public String getHistoryId() {
    return historyId;
  }

  /**
   * Sets the id of the history this collection exists within.
   * @param historyId  The id of the history this collection exists within.
   */
  public void setHistoryId(String historyId) {
    this.historyId = historyId;
  }

  /**
   * Gets the visible status of this collection.
   * @return  The visible status of this collection.
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Sets the visible status of this collection.
   * @param visible  The visible status of this collection.
   */
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(collectionType, elements, historyId, visible);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CollectionResponse) {
      CollectionResponse other = (CollectionResponse)obj;
      
      return Objects.equal(collectionType, other.collectionType) &&
          Objects.equal(elements, other.elements) && 
          Objects.equal(historyId, other.historyId) &&
          Objects.equal(visible, other.visible);
    }
    
    return false;
  }
}
