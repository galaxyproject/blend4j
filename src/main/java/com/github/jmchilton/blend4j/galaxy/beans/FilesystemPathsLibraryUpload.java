package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class FilesystemPathsLibraryUpload extends LibraryUpload {
  private boolean linkData;

  @JsonIgnore
  public boolean isLinkData() {
    return linkData;
  }

  public void setLinkData(final boolean linkData) {
    this.linkData = linkData;
  }

  public FilesystemPathsLibraryUpload() {
    this(false);
  }

  public FilesystemPathsLibraryUpload(final boolean multifile) {
    super(String.format("upload_paths%s", multifile ? "_multifile" : ""));
  }

  @JsonProperty("filesystem_paths")
  public String getFilesystemPaths() {
    return getContent();
  }

  @JsonProperty("link_data_only")
  public String getLinkDataOnly() {
    return linkData ? "link_to_files" : "copy_files";
  }
}