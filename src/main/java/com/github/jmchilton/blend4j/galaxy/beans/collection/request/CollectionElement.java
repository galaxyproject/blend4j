package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.CollectionSource;

/**
 * Class defining an element in a collection that can contain other collection elements.
 */
public class CollectionElement extends AbstractElement {

  @JsonProperty("element_identifiers")
  private List<AbstractElement> collectionElements
    = new LinkedList<AbstractElement>();
  
  @JsonProperty("collection_type")
  private String collectionType = "list";
  
  public CollectionElement() {
    setSource(CollectionSource.COLLECTION);
  }

  /**
   * Gets the list of contained collection elements.
   * @return  The list of contained collection elements.
   */
  public List<AbstractElement> getCollectionElements() {
    return collectionElements;
  }
  
  /**
   * Adds an element to this dataset collection.
   * @param collectionElement The dataset element to add.
   */
  public void addCollectionElement(AbstractElement collectionElement) {
    this.collectionElements.add(collectionElement);
  }
  
  /**
   * Gets the type of this collection.
   * @return  The type of this collection.
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
}
