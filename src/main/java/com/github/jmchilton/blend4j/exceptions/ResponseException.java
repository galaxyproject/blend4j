package com.github.jmchilton.blend4j.exceptions;

import com.github.jmchilton.blend4j.util.MoreObjects;
import com.sun.jersey.api.client.ClientResponse;

public class ResponseException extends ApiException {
  private final Integer statusCode;
  private final String rawResponse;
    
  /**
   * AMP Extension
   */  
  public ResponseException(final ClientResponse clientResponse) {
    super(clientResponse.getEntity(String.class));
    this.statusCode = clientResponse.getStatus();
    this.rawResponse = getMessage();
  }
	    
  protected MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects
            .toStringHelper(getClass())
            .add("status", statusCode)
            .add("responseBody", rawResponse);
    
  }

  @Override
  public String toString() {
    return toStringHelper().toString();
  }

  public String getResponseBody() {
    return rawResponse;
  }

  public Integer getStatusCode() {
    return statusCode;
  }
  
}
