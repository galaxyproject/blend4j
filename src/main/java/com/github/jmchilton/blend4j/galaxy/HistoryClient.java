package com.github.jmchilton.blend4j.galaxy;

import java.util.List;


import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

public class HistoryClient extends Client {
  
  HistoryClient(GalaxyInstance galaxyInstance) {
    super(galaxyInstance, "histories");
  }

  public ClientResponse create(final History history) {
    return super.create(history);   
  }

  public List<History> getHistories() {
    final TypeReference<List<History>> typeReference = new TypeReference<List<History>>() {};
    return get(typeReference);
  }

  public static class History extends GalaxyObject {
    private String name;
    
    public History() {
    }
    
    public History(final String name) {
      this.setName(name);
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
    
  }
  
  public static class PersistedHistory extends History {
    private String id;
    private String url;
    
    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }
    
    public String getUrl() {
      return url;
    }
    
    public void setUrl(final String url) {
      this.url = url;
    }

  }
}
