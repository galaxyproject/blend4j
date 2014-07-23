package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollectionResponse;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.sun.jersey.api.client.ClientResponse;

public interface HistoriesClient {
  ClientResponse createRequest(final History history);

  History create(History history);

  List<History> getHistories();

  ClientResponse showHistoryRequest(String historyId);

  HistoryDetails showHistory(String historyId);

  List<HistoryContents> showHistoryContents(String historyId);

  HistoryDetails createHistoryDataset(String historyId, HistoryDataset hd);
  
  Dataset showDataset(String historyId, String datasetId);
  
  HistoryContentsProvenance showProvenance(String historyId, String datasetId);
  
  HistoryExport exportHistory(String historyId);
  
  /**
   * Gets a Dataset collection for the given historyId and datasetCollectionId.
   * @param historyId  The ID of the history to search for dataset collections.
   * @param datasetCollectionId  The id of the dataset collection to search for.
   * @return  A DatasetCollection from the passed ids.
   */
  DatasetCollectionResponse showDatasetCollection(String historyId, String datasetCollectionId);
  
  /**
   * Creates a new Dataset Collection from the given information.
   * @param historyId  The history to store this dataset collection.
   * @param collectionDescription  A CollectionDescription describing the dataset collection to create.
   * @return  A ClientResponse describing the response.
   */
  ClientResponse createDatasetCollectionRequest(String historyId,
      DatasetCollectionDescription collectionDescription);
  
  /**
   * Creates a new Dataset Collection from the given information.
   * @param historyId  The history to store this dataset collection.
   * @param collectionDescription  A CollectionDescription describing the dataset collection to create.
   * @return  A DatasetCollection describing the created dataset collection.
   */
  DatasetCollectionResponse createDatasetCollection(String historyId,
      DatasetCollectionDescription collectionDescription);
}
