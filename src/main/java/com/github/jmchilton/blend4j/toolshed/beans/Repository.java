package com.github.jmchilton.blend4j.toolshed.beans;

public class Repository {
  private String owner;
  private String name;

  public Repository() {    
  }

  public Repository(String owner, String name) {
    this.owner = owner;
    this.name = name;
  }
  
  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 71 * hash + (this.owner != null ? this.owner.hashCode() : 0);
    hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) {
      return false;
    }
    if(getClass() != obj.getClass()) {
      return false;
    }
    final Repository other = (Repository) obj;
    if((this.owner == null) ? (other.owner != null) : !this.owner.equals(other.owner)) {
      return false;
    }
    if((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Repository{" + "owner=" + owner + ", name=" + name + '}';
  }

}
