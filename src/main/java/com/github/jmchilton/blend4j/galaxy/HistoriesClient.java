package com.github.jmchilton.blend4j.galaxy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
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
   * Deletes the given History from Galaxy.
   * @param historyId  The id of the History to delete.
   * @param purge True if this History should also be purged, otherwise set to false.
   * @return A {@link ClientResponse} for this request.
   */
  ClientResponse deleteHistoryRequest(String historyId, boolean purge);
  
  /**
   * Gets a Dataset collection for the given historyId and datasetCollectionId.
   * @param historyId  The ID of the history to search for dataset collections.
   * @param datasetCollectionId  The id of the dataset collection to search for.
   * @return  A DatasetCollection from the passed ids.
   */
  CollectionResponse showDatasetCollection(String historyId, String datasetCollectionId);
  
  /**
   * Creates a new Dataset Collection from the given information.
   * @param historyId  The history to store this dataset collection.
   * @param collectionDescription  A CollectionDescription describing the dataset collection to create.
   * @return  A ClientResponse describing the response.
   */
  ClientResponse createDatasetCollectionRequest(String historyId,
      CollectionDescription collectionDescription);
  
  /**
   * Creates a new Dataset Collection from the given information.
   * @param historyId  The history to store this dataset collection.
   * @param collectionDescription  A CollectionDescription describing the dataset collection to create.
   * @return  A DatasetCollection describing the created dataset collection.
   */
  CollectionResponse createDatasetCollection(String historyId,
      CollectionDescription collectionDescription);

  /**
   * Downloads the dataset within the given history to the passed file.
   * @param historyId  The id of the history containing the dataset.
   * @param datasetId  The id of the dataset to download.
   * @param destinationFile  The location to store the downloaded dataset.
   * @throws IOException  If there was an issue writing to the destination file.
   */
  void downloadDataset(String historyId, String datasetId, File destinationFile) throws IOException;
}
