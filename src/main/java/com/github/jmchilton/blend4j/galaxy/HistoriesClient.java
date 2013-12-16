package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDataset;
import com.sun.jersey.api.client.ClientResponse;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public interface HistoriesClient {
  ClientResponse createRequest(final History history);

  History create(History history);

  List<History> getHistories();

  ClientResponse showHistoryRequest(String historyId);

  HistoryDetails showHistory(String historyId);

  ClientResponse showHistoryContentsRequest(IndexHistoryContents request);
  
  List<HistoryContents> showHistoryContents(IndexHistoryContents request);
  
  List<HistoryContents> showHistoryContents(String historyId);

  HistoryDetails createHistoryDataset(String historyId, HistoryDataset hd);
  
  Dataset showDataset(String historyId, String datasetId);
  
  HistoryContentsProvenance showProvenance(String historyId, String datasetId);
  
  public static class IndexHistoryContents {
    private String historyId;
    private Boolean deleted;
    private Boolean visible;
    private boolean detailed = false;
    private List<String> ids;
    private List<String> types;

    public String getHistoryId() {
      return historyId;
    }

    public void setHistoryId(String historyId) {
      this.historyId = historyId;
    }

    public Boolean getDeleted() {
      return deleted;
    }

    public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
    }

    public Boolean getVisible() {
      return visible;
    }

    public void setVisible(Boolean visible) {
      this.visible = visible;
    }

    public List<String> getIds() {
      return ids;
    }

    public void setIds(List<String> ids) {
      this.ids = ids;
    }

    public List<String> getTypes() {
      return types;
    }

    public void setTypes(List<String> types) {
      this.types = types;
    }

    public boolean isDetailed() {
      return detailed;
    }

    public void setDetailed(boolean detailed) {
      this.detailed = detailed;
    }
    
    public Map<String, String> toParams() {
      final Map<String, String> params = new HashMap<String, String>();
      if(detailed) {
        params.put("details", "all");
      }
      if(types != null) {
        params.put("types", join(types));
      }
      if(ids != null) {
        params.put("ids", join(ids));
      }
      if(deleted != null) {
        params.put("deleted", deleted.toString());
      }
      if(visible != null) {
        params.put("visible", visible.toString());
      }
      return params;
    }
    
    private String join(final List<String> list) {
      final StringBuilder builder = new StringBuilder();
      boolean first = true;
      for(final String item : list) {
        if(first) {
          first = false;
        } else {
          builder.append(",");
        }
        builder.append(item);
      }
      return builder.toString();
    }
    
  }
  
}
