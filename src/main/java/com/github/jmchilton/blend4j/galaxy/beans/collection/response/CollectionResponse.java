package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;

/**
 * 
 * Represents a single element from a DatasetCollection response from Galaxy.
 */
public class CollectionResponse {
  
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
    @JsonSubTypes.Type(value = DatasetCollectionResponse.class, name="dataset_collection")
  })
  private ResponseObject responseObject;

  public String getElementIdentifier() {
    return elementIdentifier;
  }

  public void setElementIdentifier(String elementIdentifier) {
    this.elementIdentifier = elementIdentifier;
  }

  public int getElementIndex() {
    return elementIndex;
  }

  public void setElementIndex(int elementIndex) {
    this.elementIndex = elementIndex;
  }

  public String getElementType() {
    return elementType;
  }

  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModelClass() {
    return modelClass;
  }

  public void setModelClass(String modelClass) {
    this.modelClass = modelClass;
  }
  
  public ResponseObject getResponseObject() {
    return responseObject;
  }

  public void setResponseObject(ResponseObject response) {
    this.responseObject = response;
  }
  
  public String getCollectionType() {
    return collectionType;
  }

  public void setCollectionType(String collectionType) {
    this.collectionType = collectionType;
  }
}
