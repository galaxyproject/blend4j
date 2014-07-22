package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollection;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
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
  public DatasetCollection showDatasetCollection(String historyId,
      String datasetCollectionId) {
    return getWebResourceContents(historyId).path("dataset_collections").
        path(datasetCollectionId).get(DatasetCollection.class);
  }
  
  @Override
  public ClientResponse createDatasetCollectionRequest(String historyId,
      DatasetCollectionDescription collectionDescription) {
    final ClientResponse response = super.create(super.path(historyId).path("contents"), collectionDescription);
    return response;
  }
  
  public static void main(String[] args) {
    GalaxyInstance instance = GalaxyInstanceFactory.get("http://localhost:8888", "9066adc7dd6a344f1339c4b98e60a292",true);
    HistoriesClient client = instance.getHistoriesClient();
    
    DatasetCollection d = client.showDatasetCollection("63cd3858d057a6d1", "500665cb113baad6");
    System.out.println("*****");
    System.out.println(d.getName());
    
    HistoryDatasetElement dataset1 = new HistoryDatasetElement();
    dataset1.setSource("hda");
    dataset1.setId("638e3e2aad389e03");
    dataset1.setName("forward");
    HistoryDatasetElement dataset2 = new HistoryDatasetElement();
    dataset2.setSource("hda");
    dataset2.setId("1a5b83933dc4bf08");
    dataset2.setName("reverse");
    DatasetCollectionDescription description = new DatasetCollectionDescription();
    description.setCollectionType("paired");
    description.setName("collection_blend4j2");
    description.addDatasetElement(dataset1);
    description.addDatasetElement(dataset2);
    
    ClientResponse response = client.createDatasetCollectionRequest("63cd3858d057a6d1", description);
    System.out.println("response : " + response.getStatus() + " " + response.getEntity(String.class));
  }
}
