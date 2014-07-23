package com.github.jmchilton.blend4j.galaxy.beans.collection.response;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;

public class CollectionResponseCollection extends CollectionResponse implements
    ResponseObject {
  
  @JsonProperty("elements")
  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
      property="element_type")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Dataset.class, name="hda"),
    @JsonSubTypes.Type(value = CollectionResponseCollection.class, name="dataset_collection")
  })
  private List<ResponseObject> responseObjects;


  public List<ResponseObject> getResponseObjects() {
    return responseObjects;
  }

  public void setResponseObjects(List<ResponseObject> responseObjects) {
    this.responseObjects = responseObjects;
  }
}
