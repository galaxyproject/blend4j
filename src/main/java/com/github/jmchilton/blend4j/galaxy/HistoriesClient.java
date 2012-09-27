package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;;
import com.sun.jersey.api.client.ClientResponse;

public interface HistoriesClient {

  ClientResponse createRequest(final History history);

  History create(History history);

  List<History> getHistories();

  ClientResponse showHistoryRequest(String historyId);
  
  HistoryDetails showHistory(String historyId);

  List<HistoryContents>  showHistoryContentsRequest(String historyId);

  Dataset showDataset(String historyId, String datasetId);

}
