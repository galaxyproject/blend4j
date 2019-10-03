package com.github.jmchilton.blend4j.galaxy.beans;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * AMPPD extension
 * Bean for fields included in an job (an execution of a tool, which could correspond to a step in a workflow invocation), without inputs/outputs details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
