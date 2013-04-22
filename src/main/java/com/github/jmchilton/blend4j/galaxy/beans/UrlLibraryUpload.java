package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class UrlLibraryUpload extends LibraryUpload {
  protected UrlLibraryUpload() {
    super("upload_file");
  }

  @JsonProperty("files_0|url_paste")
  public String getUrl() {
    return getContent();
  }
}