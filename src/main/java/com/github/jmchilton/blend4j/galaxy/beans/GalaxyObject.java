package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GalaxyObject {
  private String id;
  private String url;

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("url")
  public void setUrl(String url) {
    this.url = url;
  }

  @JsonIgnore
  public String getUrl() {
    return url;
  }

  @JsonIgnore
  public String getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url);
  }

  @Override
  public boolean equals(Object obj) {
    
    if (obj instanceof GalaxyObject) {
      GalaxyObject other = (GalaxyObject)obj;
      
      return Objects.equals(id, other.id) &&
          Objects.equals(url, other.url);
    }
    
    return false;
  }
}
