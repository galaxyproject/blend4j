package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;

/**
 * 
 * Represents a collection of elements from a dataset collection response from Galaxy.
 */
public class CollectionElementResponse {
  
  @JsonProperty("element_identifier")
  private String elementIdentifier;
  
  @JsonProperty("element_index")
  private int elementIndex;
  
  @JsonProperty("element_type")
  private String elementType;
  
  @JsonProperty("id")
  private String id;
  
  @JsonProperty("model_class")
  private String modelClass;
  
  @JsonProperty("collection_type")
  private String collectionType;
  
  @JsonProperty("object")
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
      property="element_type")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Dataset.class, name="hda"),
    @JsonSubTypes.Type(value = CollectionResponse.class, name="dataset_collection")
  })
  private ElementResponse elementResponse;

  /**
   * Gets the identifier of the elements in this collection.
   * @return  The identifier of the elements in this collection.
   */
  public String getElementIdentifier() {
    return elementIdentifier;
  }

  /**
   * Sets the identifer of elements in this collection.
   * @param elementIdentifier  An identifier of elements in this collection.
   */
  public void setElementIdentifier(String elementIdentifier) {
    this.elementIdentifier = elementIdentifier;
  }

  /**
   * Gets the index of this particular element in a collection.
   * @return  The index of this element in a collection.
   */
  public int getElementIndex() {
    return elementIndex;
  }

  /**
   * Sets the index of this element in a collection.
   * @param elementIndex  The index of this element in a collection.
   */
  public void setElementIndex(int elementIndex) {
    this.elementIndex = elementIndex;
  }

  /**
   * Gets the type of elements in this collection.
   * @return  The type of elements in this collection.
   */
  public String getElementType() {
    return elementType;
  }

  /**
   * Sets the type of elements in this collection.
   * @param elementType  The type of elements in this collection.
   */
  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  /**
   * Gets the id of this collection element.
   * @return  The id of this collection element.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of this collection element.
   * @param id The id of this collection element.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the class of this collection.
   * @return  The class of this collection.
   */
  public String getModelClass() {
    return modelClass;
  }

  /**
   * Sets the class of this collection.
   * @param modelClass  The class of this collection.
   */
  public void setModelClass(String modelClass) {
    this.modelClass = modelClass;
  }
  
  /**
   * Gets the response element within this collection.
   * @return  The response element within this collection. 
   */
  public ElementResponse getResponseElement() {
    return elementResponse;
  }

  /**
   * Sets the data element within this collection.
   * @param response  The data element within this collection.
   */
  public void setResponseObject(ElementResponse response) {
    this.elementResponse = response;
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

  @Override
  public int hashCode() {
    return Objects.hash(collectionType, elementIdentifier, elementIndex, elementType, id, modelClass, elementResponse);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CollectionElementResponse) {
      CollectionElementResponse other = (CollectionElementResponse)obj;
      
      return Objects.equals(collectionType, other.collectionType) &&
          Objects.equals(elementIdentifier, other.elementIdentifier) &&
          Objects.equals(elementIndex, other.elementIndex) && 
          Objects.equals(elementType, other.elementType) &&
          Objects.equals(id, other.id) &&
          Objects.equals(modelClass, other.modelClass) &&
          Objects.equals(elementResponse, other.elementResponse);
    }
    
    return false;
  }
}
