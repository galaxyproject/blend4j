package com.github.jmchilton.blend4j.galaxy;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

class Client {
  private final GalaxyInstance galaxyInstance;
  private final WebResource webResource;
  private final ObjectMapper mapper = new ObjectMapper();

  public Client(final GalaxyInstance galaxyInstance, final String module) {
    this.galaxyInstance = galaxyInstance;
    this.webResource = buildWebResource(module);
  }

  GalaxyInstance getGalaxyInstance() {
    return galaxyInstance;
  }
  
  protected WebResource getWebResource() {
    return webResource;
  }
  
  private WebResource buildWebResource(final String module) {
    final ClientConfig clientConfig = new DefaultClientConfig() ;
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    final String url = galaxyInstance.getUrl();
    final WebResource webResource = com.sun.jersey.api.client.Client.create(clientConfig).resource(url);
    return webResource.queryParam("key", galaxyInstance.getKey()).path("api").path(module);
  }
  
  protected <T> T readJson(final String json, final TypeReference<T> typeReference) {
    try {
      return mapper.readValue(json, typeReference);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected ClientResponse create(final Object object) {
    return create(getWebResource(), object);
  }
  
  protected ClientResponse create(final WebResource webResource, final Object object) {
    return webResource
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .post(ClientResponse.class, object);
  }
  
  protected <T> List<T> get(final WebResource webResource, 
                             final TypeReference<List<T>> typeReference) {
    final String json = webResource
        .accept(MediaType.APPLICATION_JSON)
        .get(String.class);
    System.out.println(json);
    return readJson(json, typeReference);    
  }

  protected <T> List<T> get(final TypeReference<List<T>> typeReference) {
    return get(getWebResource(), typeReference);
  }
  
  protected <T> TypeReference<List<T>> listTypeReference(final Class<T> clazz) {
    return new TypeReference<List<T>>() {};
  }
  
}