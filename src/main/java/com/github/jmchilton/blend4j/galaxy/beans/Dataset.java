package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Dataset extends HistoryDetails implements HasGalaxyUrl {
    private String dataType;
    private boolean deleted;
    private String downloadUrl;
    private Integer fileSize;
    private String genomeBuild;
    private boolean visible;
    private String galaxyUrl;
    
    public boolean getDeleted() {
        return deleted;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
      return getGalaxyUrl() + getDownloadUrl();
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
    public void setGalaxyUrl(final String galaxyUrl) {
      this.galaxyUrl = galaxyUrl;
    }
    
    @JsonIgnore
    public String getGalaxyUrl() {
      return galaxyUrl;
    }

}
