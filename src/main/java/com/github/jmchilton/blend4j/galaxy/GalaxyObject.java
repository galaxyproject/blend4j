package com.github.jmchilton.blend4j.galaxy;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;

public class GalaxyObject {
  private Map<String, Object> extraProperties = new HashMap<String, Object>(); 

  @JsonAnySetter
  public void setProperty(final String propertyName, Object propertyValue) {
    this.extraProperties.put(propertyName, propertyValue);
  }
  
  @JsonIgnore
  public String getUrl() {
    return extraProperties.get("url").toString();
  }
  
  @JsonIgnore
  public String getId() {
    return extraProperties.get("id").toString();
  }
  
}