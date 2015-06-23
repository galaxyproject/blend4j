package com.github.jmchilton.blend4j.galaxy.beans;

import com.github.jmchilton.blend4j.util.Objects;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.response.ElementResponse;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Dataset extends HistoryContents implements HasGalaxyUrl, ElementResponse {
  private String dataType = null;
  private String fileExt = null;
  private String downloadUrl;
  private Long fileSize;
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

  /**
 * @deprecated  As of 1.2 release, replaced by {@link #getDataTypeExt()}.
 */
  @Deprecated
  public String getDataType() {
    return getDataTypeExt();
  }

  public String getDataTypeExt() {
    // Hacked up due to backard incompatible changes made to the
    // Galaxy API as of the October 2014 release of Galaxy.
    // https://bitbucket.org/galaxy/galaxy-central/commits/9d152ed
    if(this.fileExt != null) {
      return this.fileExt;
    } else {
      return dataType;
    }
  }

  /**
   * This returns the Python module and class of the data type corresponding
   * to this object. (Starting from the October 2014 version of Galaxy.)
   *
   */
  public String getDataTypeClass() {
    return dataType;
  }

  @JsonProperty("data_type")
  public void setDataType(String dataType) {
    // Meanaing of this property changed with October 2014 release (see note
    // above).
    this.dataType = dataType;
  }

  @JsonProperty("file_ext")
  public void setFileExt(final String fileExt) {
    this.fileExt = fileExt;
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

  public Long getFileSize() {
    return fileSize;
  }

  @JsonProperty("file_size")
  public void setFileSize(Long fileSize) {
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
