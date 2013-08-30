package com.github.jmchilton.blend4j.toolshed.beans;

import com.github.jmchilton.blend4j.galaxy.beans.GalaxyObject;
import org.codehaus.jackson.annotate.JsonProperty;

public class RepositoryDetails extends GalaxyObject {
  @JsonProperty("times_downloaded")
  private int timesDownloaded;
  @JsonProperty("user_id")
  private String userId;
  private String description;
  private boolean deleted;
  private boolean deprecated;
  @JsonProperty("private")
  private boolean private_;
  private String owner;
  private String type;
  private String name;

  public int getTimesDownloaded() {
    return timesDownloaded;
  }

  public void setTimesDownloaded(int timesDownloaded) {
    this.timesDownloaded = timesDownloaded;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isDeprecated() {
    return deprecated;
  }

  public void setDeprecated(boolean deprecated) {
    this.deprecated = deprecated;
  }

  public boolean isPrivate_() {
    return private_;
  }

  public void setPrivate_(boolean private_) {
    this.private_ = private_;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
