package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class FilesystemPathsLibraryUpload extends LibraryUpload {

  public FilesystemPathsLibraryUpload() {
    super("upload_paths");
  }

  @JsonProperty("filesystem_paths")
  public String getFilesystemPaths() {
    return getContent();
  }

}