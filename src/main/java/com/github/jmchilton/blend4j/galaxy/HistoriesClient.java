package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.sun.jersey.api.client.ClientResponse;

public interface HistoriesClient {

  ClientResponse createRequest(final History history);

  History create(History history);

  List<History> getHistories();

  ClientResponse showHistoryRequest(String historyId);
  
  HistoryDetails showHistory(String historyId);

  ClientResponse showHistoryContentsRequest(String historyId);
  
}