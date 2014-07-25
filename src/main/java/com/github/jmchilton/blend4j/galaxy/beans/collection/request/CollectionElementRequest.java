package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.CollectionSource;

/**
 * Class defining an element in a collection that can contain other collection elements.
 */
public class CollectionElementRequest extends CollectionRequest {

  @JsonProperty("element_identifiers")
  private List<CollectionRequest> collectionElements
    = new LinkedList<CollectionRequest>();
  
  @JsonProperty("collection_type")
  private String collectionType = "list";
  
  public CollectionElementRequest() {
    setSource(CollectionSource.COLLECTION);
  }

  /**
   * Gets the list of contained collection elements.
   * @return  The list of contained collection elements.
   */
  public List<CollectionRequest> getCollectionElements() {
    return collectionElements;
  }
  
  /**
   * Adds an element to this dataset collection.
   * @param collectionElement The dataset element to add.
   */
  public void addCollectionElement(CollectionRequest collectionElement) {
    this.collectionElements.add(collectionElement);
  }
  
  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }
}
