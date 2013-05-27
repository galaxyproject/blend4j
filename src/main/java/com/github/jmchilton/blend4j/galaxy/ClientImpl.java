package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.HasGalaxyUrl;
import com.sun.jersey.api.client.ClientRequest;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import java.io.File;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

class ClientImpl {
  private final GalaxyInstanceImpl galaxyInstance;
  private final WebResource webResource;
  private final ObjectMapper mapper = new ObjectMapper();

  ClientImpl(final GalaxyInstanceImpl galaxyInstance, final String module) {
    this.galaxyInstance = galaxyInstance;
    this.webResource = buildWebResource(module);
  }

  protected String write(final Object object) {
    try {
      return mapper.writer().writeValueAsString(object);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  GalaxyInstance getGalaxyInstance() {
    return galaxyInstance;
  }

  protected WebResource getWebResource() {
    return webResource;
  }

  protected WebResource getWebResource(final String id) {
    return getWebResource().path(id);
  }

  protected WebResource getWebResourceContents(final String id) {
    return getWebResource(id).path("contents");
  }

  private WebResource buildWebResource(final String module) {
    final WebResource webResource = galaxyInstance.getWebResource();
    return webResource.path(module);
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
    return readJson(json, typeReference);
  }

  protected <T> T show(final String id, Class<T> clazz) {
    return getWebResource().path(id).get(clazz);
  }

  protected <T> List<T> get(final TypeReference<List<T>> typeReference) {
    return get(getWebResource(), typeReference);
  }

  protected <T> TypeReference<List<T>> listTypeReference(final Class<T> clazz) {
    return new TypeReference<List<T>>() {
    };
  }

  protected <T extends HasGalaxyUrl> T setGalaxyUrl(final T bean) {
    bean.setGalaxyUrl(galaxyInstance.getGalaxyUrl());
    bean.setApiKey(galaxyInstance.getApiKey());
    return bean;
  }

  protected ClientResponse multipartPost(final WebResource resource,
                                         final Map<String, Object> fields,
                                         final Iterable<BodyPart> bodyParts) {
    final WebResource.Builder builder = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE);
    final FormDataMultiPart multiPart = new FormDataMultiPart();
    for(final Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
      final FormDataBodyPart bp = new FormDataBodyPart(fieldEntry.getKey(), fieldEntry.getValue(), MediaType.APPLICATION_JSON_TYPE);
      multiPart.bodyPart(bp);
    }

    for(final BodyPart bodyPart : bodyParts) {
      bodyPart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
      multiPart.bodyPart(bodyPart);
    }
    return builder.post(ClientResponse.class, multiPart);
  }

  protected Iterable<BodyPart> prepareUpload(final File file) {
    final FileDataBodyPart fdbp = new FileDataBodyPart("files_0|file_data", file);
    return Arrays.<BodyPart>asList(fdbp);
  }
}
