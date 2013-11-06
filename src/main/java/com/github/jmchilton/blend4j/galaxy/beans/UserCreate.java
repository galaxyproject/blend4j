package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserCreate {
  private String username;
  private String email;
  private String password;

  @JsonProperty
  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  @JsonProperty
  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @JsonProperty
  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
  

}
