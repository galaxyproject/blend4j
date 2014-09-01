package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * An object representing a dataset within a Library in Galaxy.
 * 
 * @author Aaron Petkau <aaron.petkau@phac-aspc.gc.ca>
 *
 */
public class LibraryDataset extends GalaxyObject {

  @JsonProperty("data_type")
  private String dataType;

  @JsonProperty("date_uploaded")
  private String dateUploaded;

  @JsonProperty("file_name")
  private String fileName;

  @JsonProperty("file_size")
  private String fileSize;

  @JsonProperty("folder_id")
  private String folderId;

  @JsonProperty("genome_build")
  private String genomeBuild;

  @JsonProperty("id")
  private String id;

  @JsonProperty("ldda_id")
  private String lddaId;

  @JsonProperty("message")
  private String message;

  @JsonProperty("metadata_data_lines")
  private String metadataDataLines;

  @JsonProperty("metadata_dbkey")
  private String metadataDbkey;

  @JsonProperty("metadata_sequences")
  private String metadataSequences;

  @JsonProperty("misc_blurb")
  private String miscBlurb;

  @JsonProperty("misc_info")
  private String miscInfo;

  @JsonProperty("model_class")
  private String modelClass;

  @JsonProperty("name")
  private String name;

  @JsonProperty("parent_library_id")
  private String parentLibraryId;

  @JsonProperty("peek")
  private String peek;

  @JsonProperty("state")
  private String state;

  // TODO ignore this for now as it's a more complicated datatype
//  @JsonProperty("template_data")
//  private String templateData;

  @JsonProperty("uploaded_by")
  private String uploadedBy;

  @JsonProperty("uuid")
  private String uuid;

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getDateUploaded() {
    return dateUploaded;
  }

  public void setDateUploaded(String dateUploaded) {
    this.dateUploaded = dateUploaded;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public String getFolderId() {
    return folderId;
  }

  public void setFolderId(String folderId) {
    this.folderId = folderId;
  }

  public String getGenomeBuild() {
    return genomeBuild;
  }

  public void setGenomeBuild(String genomeBuild) {
    this.genomeBuild = genomeBuild;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLddaId() {
    return lddaId;
  }

  public void setLddaId(String lddaId) {
    this.lddaId = lddaId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMetadataDataLines() {
    return metadataDataLines;
  }

  public void setMetadataDataLines(String metadataDataLines) {
    this.metadataDataLines = metadataDataLines;
  }

  public String getMetadataDbkey() {
    return metadataDbkey;
  }

  public void setMetadataDbkey(String metadataDbkey) {
    this.metadataDbkey = metadataDbkey;
  }

  public String getMetadataSequences() {
    return metadataSequences;
  }

  public void setMetadataSequences(String metadataSequences) {
    this.metadataSequences = metadataSequences;
  }

  public String getMiscBlurb() {
    return miscBlurb;
  }

  public void setMiscBlurb(String miscBlurb) {
    this.miscBlurb = miscBlurb;
  }

  public String getMiscInfo() {
    return miscInfo;
  }

  public void setMiscInfo(String miscInfo) {
    this.miscInfo = miscInfo;
  }

  public String getModelClass() {
    return modelClass;
  }

  public void setModelClass(String modelClass) {
    this.modelClass = modelClass;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentLibraryId() {
    return parentLibraryId;
  }

  public void setParentLibraryId(String parentLibraryId) {
    this.parentLibraryId = parentLibraryId;
  }

  public String getPeek() {
    return peek;
  }

  public void setPeek(String peek) {
    this.peek = peek;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getUploadedBy() {
    return uploadedBy;
  }

  public void setUploadedBy(String uploadedBy) {
    this.uploadedBy = uploadedBy;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }
}
