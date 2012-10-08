package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class User extends GalaxyObject {
  private String email;
  private String username;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @JsonProperty("nice_total_disk_usage")
  public void setNiceTotalDiskUsage(final String niceTotalDiskUsage) {
    
  }
  
  @JsonProperty("total_disk_usage")  
  public void setTotalDiskUsage(final String totalDiskUsage) {
    
  }
  
  public void setUsername(final String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
  
}
