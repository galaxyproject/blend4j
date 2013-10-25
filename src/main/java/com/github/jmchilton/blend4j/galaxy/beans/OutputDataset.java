package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;


public class OutputDataset extends Dataset {
  private String outputName;

  public String getOutputName() {
    return outputName;
  }

  @JsonProperty("output_name")
  public void setOutputName(String outputName) {
    this.outputName = outputName;
  }
  
  
  
  
}
