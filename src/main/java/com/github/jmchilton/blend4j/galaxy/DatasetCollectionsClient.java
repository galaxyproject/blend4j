package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.GalaxyObject;
import com.sun.jersey.api.client.ClientResponse;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public interface DatasetCollectionsClient {

  public static enum Source {
    HDA("hda"), LDDA("ldda");
    private String value;

    private Source(final String value) {
      this.value = value;
    }

    public String toJson() {
      return value;
    }
  }
  
  public static class DatasetIdentifier {
    private Source src = Source.HDA;
    private String id;
    
    public DatasetIdentifier() {
      
    }
    
    public DatasetIdentifier(final String id) {
      this(id, Source.HDA);
    }
    
    public DatasetIdentifier(final String id, final Source src) {
      this.id = id;
      this.src = src;
    }

    public String getSrc() {
      return src.toJson();
    }

    public void setSrc(final Source src) {
      this.src = src;
    }

    public String getId() {
      return id;
    }

    public void setId(final String id) {
      this.id = id;
    }
    
  }
  
  public static class DatasetCollectionInstanceCreate {
    @JsonProperty("collection_type")
    private String collectionType;
    private String name;
    @JsonProperty("dataset_identifiers")
    private Map<String, DatasetIdentifier> datasetIdentifiers;

    public String getCollectionType() {
      return collectionType;
    }

    public void setCollectionType(String collectionType) {
      this.collectionType = collectionType;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Map<String, DatasetIdentifier> getDatasetIdentifiers() {
      return datasetIdentifiers;
    }
    
    public void setDatasetIdentifiers(final Map<String, DatasetIdentifier> datasetIdentifiers) {
      this.datasetIdentifiers = datasetIdentifiers;
    }

    
  }
  
  public static class HistoryDatasetCollectionInstanceCreate extends DatasetCollectionInstanceCreate {
    @JsonProperty("history_id")
    private String historyId;

    public String getHistoryId() {
      return historyId;
    }

    public void setHistoryId(String historyId) {
      this.historyId = historyId;
    }

  }

  public static class LibraryDatasetCollectionInstanceCreate extends DatasetCollectionInstanceCreate {
    @JsonProperty("folder_id")
    private String folderId;

    public String getFolderId() {
      return folderId;
    }

    public void setFolderId(String folderId) {
      this.folderId = folderId;
    }

  }
  
  public static class DatasetCollectionInstanceSummary extends GalaxyObject {
  }

  ClientResponse createRequest(DatasetCollectionInstanceCreate create);

  DatasetCollectionInstanceSummary create(DatasetCollectionInstanceCreate create);
  
}


