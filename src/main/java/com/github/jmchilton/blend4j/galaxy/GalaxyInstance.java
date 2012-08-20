package com.github.jmchilton.blend4j.galaxy;

public class GalaxyInstance {
  private String url;
  private String key;
  
  private GalaxyInstance(final String url, final String key) {
    this.url = url;
    this.setKey(key);
	}
  
  public static GalaxyInstance getInstance(final String url, final String key) {
    return new GalaxyInstance(url, key);
  }

  public String getUrl() {
    return url;
  }
  
  public HistoryClient getHistoryClient() {
    return new HistoryClient(this);
  }

  public LibraryClient getLibraryClient() {
    return new LibraryClient(this);
  }
  
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}