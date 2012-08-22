package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.sun.jersey.api.client.ClientResponse;

class HistoriesClientImpl extends ClientImpl implements HistoriesClient {

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

  public ClientResponse showHistoryContentsRequest(String historyId) {
    return super.getWebResourceContents(historyId).get(ClientResponse.class);
  }


}
