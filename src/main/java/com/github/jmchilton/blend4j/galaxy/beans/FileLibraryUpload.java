package com.github.jmchilton.blend4j.galaxy.beans;

import java.io.File;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class FileLibraryUpload extends LibraryUpload {
  private File file;

  public FileLibraryUpload() {
    super("upload_file");
  }

  public void setFile(final File file) {
    this.file = file;
  }

  @JsonIgnore
  public File getFile() {
    return file;
  }
}
