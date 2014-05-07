package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HistoryContents extends GalaxyObject {
  private String name;
  private String type = "file";
  private boolean deleted = false;
  private boolean purged = false;
  private int hid;
  private String historyContentType = "dataset";
  private String state;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Deprecated
  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }
  
  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isPurged() {
    return purged;
  }

  public void setPurged(boolean purged) {
    this.purged = purged;
  }

  public int getHid() {
    return hid;
  }

  public void setHid(int hid) {
    this.hid = hid;
  }

  public String getHistoryContentType() {
    return historyContentType;
  }

  @JsonProperty("history_content_type")
  public void setHistoryContentType(String historyContentType) {
    this.historyContentType = historyContentType;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
