package com.github.jmchilton.blend4j.galaxy.beans.dataset;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Class defining an element in a collection that can contain other collection elements.
 */
public class CollectionElementDescription extends CollectionDescription {

  @JsonProperty("element_identifiers")
  private List<CollectionDescription> collectionElements
    = new LinkedList<CollectionDescription>();
  
  @JsonProperty("collection_type")
  private String collectionType = "list";
  
  public CollectionElementDescription() {
    setSource(CollectionSource.COLLECTION);
  }

  /**
   * Gets the list of contained collection elements.
   * @return  The list of contained collection elements.
   */
  public List<CollectionDescription> getCollectionElements() {
    return collectionElements;
  }
  
  /**
   * Adds an element to this dataset collection.
   * @param datasetElement The dataset element to add.
   */
  public void addCollectionElement(CollectionDescription collectionElement) {
    this.collectionElements.add(collectionElement);
  }
  
  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }
}
