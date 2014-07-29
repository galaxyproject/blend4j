package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.exceptions.ResponseException;
import com.github.jmchilton.blend4j.util.MoreObjects;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class GalaxyResponseException extends ResponseException {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final TypeReference<Map<String, String>> ERROR_MAP = new TypeReference<Map<String, String>>() {
  };
  public static final String DEFAULT_ERROR_MESSAGE = null;
  public static final Integer DEFAULT_ERROR_CODE = null;
  public static final String DEFAULT_TRACEBACK = null;
  
  private String errorMessage = DEFAULT_ERROR_MESSAGE;
  private Integer errorCode = DEFAULT_ERROR_CODE;
  private String traceback = DEFAULT_TRACEBACK;
  
  public GalaxyResponseException(ClientResponse clientResponse) {
    super(clientResponse);
    try {
      final Map<String, String> errorMap = OBJECT_MAPPER.readValue(getResponseBody(), ERROR_MAP);
      if(errorMap.containsKey("err_msg")) {
        errorMessage = errorMap.get("err_msg");
      }
      if(errorMap.containsKey("err_code")) {
        try {
          errorCode = Integer.parseInt(errorMap.get("err_code"));
        } catch(NumberFormatException exception) {
        }
      }
      if(errorMap.containsKey("traceback")) {
        traceback = errorMap.get("traceback");
      }
    } catch(final RuntimeException exception) {
    } catch(final IOException exception) {
      // Not all Galaxy behave well - many still don't return JSON responses,
      // that is most likely what happened here.
    }
  }
  
  protected MoreObjects.ToStringHelper toStringHelper() {
    return super.toStringHelper()
            .add("errorMessage", errorMessage)
            .add("errorCode", errorCode)
            .add("traceback", traceback);            
  }
  
}
