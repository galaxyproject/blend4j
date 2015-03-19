package com.github.jmchilton.blend4j.galaxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDeleteResponse;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

class HistoriesClientImpl extends Client implements HistoriesClient {
  HistoriesClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "histories");
  }

  public ClientResponse createRequest(final History history) {
    return super.create(history);
  }

  public History create(final History history) {
    return createRequest(history).getEntity(History.class);
  }

  public List<History> getHistories() {
    final TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {
    };
    return get(typeReference);
  }

  public ClientResponse showHistoryRequest(String id) {
    return super.show(id, ClientResponse.class);
  }

  public HistoryDetails showHistory(String id) {
    return super.show(id, HistoryDetails.class);
  }

  public List<HistoryContents> showHistoryContents(String historyId) {
    return get(getWebResourceContents(historyId), new TypeReference<List<HistoryContents>>() {
    });
  }

  public Dataset showDataset(String historyId, String datasetId) {
    return setGalaxyUrl(getWebResourceContents(historyId).path(datasetId).get(Dataset.class));
  }

  public HistoryContentsProvenance showProvenance(String historyId, String datasetId) {
    return getWebResourceContents(historyId).path(datasetId).path("provenance").get(HistoryContentsProvenance.class);
  }


  public HistoryDetails createHistoryDataset(String historyId, HistoryDataset hd) {
    final ClientResponse response = super.create(super.path(historyId).path("contents"), hd);
    return response.getEntity(HistoryDetails.class);
  }

  public HistoryExport exportHistory(String historyId) {
    final WebResource.Builder resource = super.path(historyId).path("exports").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    final ClientResponse response = resource.put(ClientResponse.class);
    final int status = response.getStatus();
    if(status == 200) {
      return response.getEntity(HistoryExport.class);
    } else if(status == 202) {
      return new HistoryExport();
    } else {
      throw new RuntimeException("Problems with history export.");
    }
  }

  @Override
  public CollectionResponse showDatasetCollection(String historyId,
      String datasetCollectionId) {
    return getWebResourceContents(historyId).path("dataset_collections").
        path(datasetCollectionId).get(CollectionResponse.class);
  }
  
  @Override
  public ClientResponse createDatasetCollectionRequest(String historyId,
      CollectionDescription collectionDescription) {
    final ClientResponse response = super.create(super.path(historyId).path("contents"), collectionDescription);
    return response;
  }

  @Override
  public CollectionResponse createDatasetCollection(String historyId,
      CollectionDescription collectionDescription) {
    ClientResponse response = createDatasetCollectionRequest(historyId, collectionDescription);
    
    if (response.getStatus() == 200) {
      return response.getEntity(CollectionResponse.class);
    } else {
      throw new RuntimeException("Error creating dataset collection, status=" + response.getStatus() +
          " returned=" + response.getEntity(String.class));
    }
  }

  @Override
  public void downloadDataset(String historyId, String datasetId,
      File destinationFile) throws IOException {
    File downloadedFile = super.getWebResourceContents(historyId)
        .path(datasetId).path("display").get(File.class);
    downloadedFile.renameTo(destinationFile);
    FileWriter fr = new FileWriter(downloadedFile);
    fr.close();
  }

  @Override
  public ClientResponse deleteHistoryRequest(String historyId, boolean purge) {
    Map<String,Boolean> deleteStatus = new HashMap<String,Boolean>();
    if (purge) {
      deleteStatus.put("purge", true);
    }
  	return deleteResponse(getWebResource(historyId), write(deleteStatus));
  }
  
  @Override
  public HistoryDeleteResponse deleteHistory(String historyId, boolean purge) {
    return deleteHistoryRequest(historyId, purge).getEntity(HistoryDeleteResponse.class);
  }
}
