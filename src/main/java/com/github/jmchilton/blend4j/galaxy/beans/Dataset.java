package com.github.jmchilton.blend4j.galaxy.beans;

import com.github.jmchilton.blend4j.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.response.ElementResponse;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Dataset extends HistoryContents implements HasGalaxyUrl, ElementResponse {
  private String dataType;
  private String downloadUrl;
  private Integer fileSize;
  private String genomeBuild;
  private boolean visible;
  private String galaxyUrl;
  private String apiKey;
  private String info;
  private String blurb;

  public String getBlurb() {
    return blurb;
  }

  @JsonProperty("misc_blurb")
  public void setBlurb(String blurb) {
    this.blurb = blurb;
  }
  
  public String getInfo() {
    return info;
  }

  @JsonProperty("misc_info")
  public void setInfo(String info) {
    this.info = info;
  }
  
  
  public boolean getVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public String getDataType() {
    return dataType;
  }

  @JsonProperty("data_type")
  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  @JsonProperty("download_url")
  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  @JsonIgnore
  public String getFullDownloadUrl() {
    return String.format("%s%s?key=%s",
                         getGalaxyUrl(),
                         getDownloadUrl(),
                         this.apiKey);
  }

  public String getGenomeBuild() {
    return genomeBuild;
  }

  @JsonProperty("genome_build")
  public void setGenomeBuild(String genomeBuild) {
    this.genomeBuild = genomeBuild;
  }

  public Integer getFileSize() {
    return fileSize;
  }

  @JsonProperty("file_size")
  public void setFileSize(Integer fileSize) {
    this.fileSize = fileSize;
  }
  
  @JsonIgnore
  public void setApiKey(final String apiKey) {
    this.apiKey = apiKey;
  }

  @JsonIgnore
  public void setGalaxyUrl(final String galaxyUrl) {
    this.galaxyUrl = galaxyUrl;
  }

  @JsonIgnore
  public String getGalaxyUrl() {
    return galaxyUrl;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(apiKey, blurb, dataType, downloadUrl,
        fileSize, galaxyUrl, genomeBuild, info, visible);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Dataset) {
      Dataset other = (Dataset)obj;
      
      return Objects.equal(apiKey, other.apiKey) &&
          Objects.equal(blurb, other.blurb) &&
          Objects.equal(dataType, other.dataType) &&
          Objects.equal(downloadUrl, other.downloadUrl) &&
          Objects.equal(fileSize, other.fileSize) &&
          Objects.equal(galaxyUrl, other.galaxyUrl) &&
          Objects.equal(genomeBuild, other.genomeBuild) &&
          Objects.equal(info, other.info) && 
          Objects.equal(visible, other.visible);
    }
      
    return false;
  }
}
