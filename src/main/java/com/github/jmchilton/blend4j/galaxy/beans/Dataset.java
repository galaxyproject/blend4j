package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.github.jmchilton.blend4j.galaxy.beans.collection.response.ElementResponse;
import com.github.jmchilton.blend4j.util.Objects;

/**
 * AMPPD extension
 * Bean containing fields for a dataset accessible via Galaxy REST API.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Dataset extends HistoryContents implements HasGalaxyUrl, ElementResponse {
	/* Note:
	 * The fields returned by querying dataset vs history content are identical in Galaxy API; however, AMPPD only needs to use dataset, 
	 * thus, HistoryContent is not extended for simplicity and avoiding conflicts with other parts of blend4j.
	 * Meanwhile, some fields from Galaxy API that are redundant or not needed by AMPPD are not added here:
	 * accessible, type_id, display_types, display_apps, metadata_dbkey, peek, meta_files, permissions, extension, url, visualizations, rerunnable, api_type
	 */

	@JsonProperty("file_name")	
	private String fileName;			// absolute path of the dataset file in the Galaxy file system

	private Boolean resubmitted;
	
	@JsonProperty("create_time")	
	private Date createTime;

	@JsonProperty("creating_job")	
	private String creatingJob;			// ID of the Galaxy job that created this dataset

	@JsonProperty("dataset_id")	
	private String datasetId;			// some internal ID, not to be confused with the dataset API ID		

	@JsonProperty("hda_ldda")	
	private String hdaLdda;			

	@JsonProperty("update_time")	
	private Date updateTime;

	private List<String> tags = new ArrayList<String>();

	@JsonProperty("history_id")	
	private String historyId;		

	private String annotation;		
	private String dataType = null;		// data_type: Galaxy data type class
	private String fileExt = null;		// file_ext = extension
	private String downloadUrl;			// download_url
	private Integer fileSize;			// file_size
	private String genomeBuild;			// genome_build
	private boolean visible;
	private String info;				// misc_info
	private String blurb;				// misc_blurb: similar to file_size
	private String galaxyUrl;			// not part of REST API response
	private String apiKey;				// not part of REST API response
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Boolean getResubmitted() {
		return resubmitted;
	}

	public void setResubmitted(Boolean resubmitted) {
		this.resubmitted = resubmitted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatingJob() {
		return creatingJob;
	}

	public void setCreatingJob(String creatingJob) {
		this.creatingJob = creatingJob;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getHdaLdda() {
		return hdaLdda;
	}

	public void setHdaLdda(String hdaLdda) {
		this.hdaLdda = hdaLdda;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}	

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

	public String getFileExt() {
		return fileExt;
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
