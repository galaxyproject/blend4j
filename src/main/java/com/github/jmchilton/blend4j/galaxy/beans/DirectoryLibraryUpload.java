package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class DirectoryLibraryUpload extends LibraryUpload {

  public DirectoryLibraryUpload() {
    super("upload_directory");
  }

  @JsonProperty("server_dir")
  public String getServerDirectory() {
    return getContent();
  }

}