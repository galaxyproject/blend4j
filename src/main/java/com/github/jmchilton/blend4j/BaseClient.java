package com.github.jmchilton.blend4j;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class BaseClient {
  protected final ObjectMapper mapper = new ObjectMapper();
  protected final WebResource webResource;

  public BaseClient(final WebResource baseWebResource,
                    final String module) {
    this.webResource = baseWebResource.path(module);
  }

  protected ClientResponse create(final Object object) {
    return create(getWebResource(), object);
  }

  protected ClientResponse create(final WebResource webResource, final Object object) {
    return webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, object);
  }

  protected <T> List<T> get(final WebResource webResource, final TypeReference<List<T>> typeReference) {
    final String json = getJson(webResource);
    return readJson(json, typeReference);
  }

  protected <T> List<T> get(final TypeReference<List<T>> typeReference) {
    return get(getWebResource(), typeReference);
  }
  
  protected ClientResponse getResponse(final WebResource webResource) {
    return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
  }

  protected String getJson(final WebResource webResource) {
    final String json = webResource.accept(MediaType.APPLICATION_JSON).get(String.class);
    return json;
  }

  protected WebResource getWebResource() {
    return webResource;
  }

  protected WebResource getWebResource(final String id) {
    return path(id);
  }

  protected WebResource getWebResourceContents(final String id) {
    return getWebResource(id).path("contents");
  }
  
  protected WebResource path(final String path) {
    return getWebResource().path(path);
  }

  protected <T> TypeReference<List<T>> listTypeReference(final Class<T> clazz) {
    return new TypeReference<List<T>>() {
    };
  }

  protected ClientResponse multipartPost(final WebResource resource, final Map<String, Object> fields, final Iterable<BodyPart> bodyParts) {
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
    return prepareUploads(Arrays.asList(file));
  }

  protected Iterable<BodyPart> prepareUploads(final Iterable<File> files) {
    final List<BodyPart> bodyParts = new ArrayList<BodyPart>();
    for(final File file : files) {
      final String paramName = "files_0|file_data";
      final FileDataBodyPart fdbp = new FileDataBodyPart(paramName, file);
      bodyParts.add(fdbp);
    }
    return bodyParts;
  }

  protected <T> T readJson(final String json, final TypeReference<T> typeReference) {
    try {
      return mapper.readValue(json, typeReference);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  protected <T> T read(final ClientResponse response, final TypeReference<T> typeReference) {
    return readJson(response.getEntity(String.class), typeReference);
  }

  protected <T> T show(final String id, Class<T> clazz) {
    return getWebResource(id).get(clazz);
  }

  protected String write(final Object object) {
    try {
      return mapper.writer().writeValueAsString(object);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

}
