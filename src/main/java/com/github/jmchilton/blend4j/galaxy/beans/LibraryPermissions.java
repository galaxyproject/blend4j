package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class LibraryPermissions {
  private List<String> accessInRoles = new ArrayList<String>();
  private List<String> modifyInRoles = new ArrayList<String>();
  private List<String> addInRoles = new ArrayList<String>();
  private List<String> manageInRoles = new ArrayList<String>();

  @JsonProperty("LIBRARY_ACCESS_in")
  public List<String> getAccessInRoles() {
    return accessInRoles;
  }
  
  public void setAccessInRoles(List<String> accessInRoles) {
    this.accessInRoles = accessInRoles;
  }
  
  @JsonProperty("LIBRARY_MODIFY_in")
  public List<String> getModifyInRoles() {
    return modifyInRoles;
  }
  
  public void setModifyInRoles(List<String> modifyInRoles) {
    this.modifyInRoles = modifyInRoles;
  }
  
  @JsonProperty("LIBRARY_ADD_in")
  public List<String> getAddInRoles() {
    return addInRoles;
  }
  
  public void setAddInRoles(List<String> addInRoles) {
    this.addInRoles = addInRoles;
  }
  
  @JsonProperty("LIBRARY_MANAGE_in")
  public List<String> getManageInRoles() {
    return manageInRoles;
  }
  
  public void setManageInRoles(List<String> manageInRoles) {
    this.manageInRoles = manageInRoles;
  }

}
