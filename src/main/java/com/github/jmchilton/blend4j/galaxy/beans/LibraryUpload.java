package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class LibraryUpload extends GalaxyObject {
  private String folderId;
  private String fileType = "auto";
  private String dbkey = "?";
  private String content;
  private final String uploadOption;
  
  protected LibraryUpload(final String uploadOption) {
    this.uploadOption = uploadOption;
  }
  
  @JsonProperty("upload_option")
  public String getUploadOption() {
    return uploadOption;
  }

  @JsonIgnore
  public String getContent() {
    return content;
  }
  
  @JsonIgnore
  public void setContent(final String content) {
    this.content = content;
  }

  @JsonProperty("folder_id")
  public String getFolderId() {
    return folderId;
  }

  public void setFolderId(String folderId) {
    this.folderId = folderId;
  }

  @JsonProperty("file_type")
  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getDbkey() {
    return dbkey;
  }

  public void setDbkey(String dbkey) {
    this.dbkey = dbkey;
  }

  @JsonProperty("create_type")
  public String getCreateType() {
    return "file";
  }

}