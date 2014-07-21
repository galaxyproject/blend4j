package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;

/**
 *  Class for storing information on DatasetCollections within a History.
 */
public class DatasetCollection extends HistoryContents {
  
  private List<HistoryContents> elements;
  private String collection_type;
  
  
  public List<HistoryContents> getElements() {
    return elements;
  }
  public void setElements(List<HistoryContents> elements) {
    this.elements = elements;
  }
  public String getCollection_type() {
    return collection_type;
  }
  public void setCollection_type(String collection_type) {
    this.collection_type = collection_type;
  }
}
