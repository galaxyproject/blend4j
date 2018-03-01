package com.github.jmchilton.blend4j.galaxy.beans;


import org.codehaus.jackson.annotate.JsonProperty;

public class JobInputOutput extends GalaxyObject {
  public static enum Source {
    library(), hda(), ldda();

    private Source() {

    }
    
    public String toJson() {
      return name();
    }    
    
  }

  @JsonProperty("src")
  private Source source;

  public String getSource() {
    return source.name();
  }
  
  public void setSource(final Source source) {
    this.source = source;
  }
}
