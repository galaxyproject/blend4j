package com.github.jmchilton.blend4j.galaxy.beans;

public class HistoryContentsDataset extends HistoryContents {
  private int hid;
  private String state;

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getHid() {
    return hid;
  }

  public void setHid(int hid) {
    this.hid = hid;
  }
  
}
