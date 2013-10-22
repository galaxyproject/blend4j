package com.github.jmchilton.blend4j.galaxy.beans;


public class HistoryDataset {
  public static enum Source {
    LIBRARY("library"), HDA("hda");
    
    private String source;
    
    private Source(final String source) {
      this.source = source;
    }
    
    public String toJson() {
      return source;
    }    
    
  }

  private Source source;
  private String content;

  public String getContent() {
    return content;
  }
  
  public String getSource() {
    return source.toJson();
  }
  
  public void setSource(final Source source) {
    this.source = source;
  }
  
  public void setContent(final String content) {
    this.content = content;
  }
  
}
