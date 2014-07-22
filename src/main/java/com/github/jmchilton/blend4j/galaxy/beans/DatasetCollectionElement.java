package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * Represents a single element within a DatasetCollection.
 */
public class DatasetCollectionElement {
  
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
  
  @JsonProperty("object")
  private Dataset dataset;

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

  public Dataset getDataset() {
    return dataset;
  }

  public void setDataset(Dataset dataset) {
    this.dataset = dataset;
  }
}
