package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Map;

/**
 * Build a WebResourceFactory from Galaxy credentials (username/password).
 * If Galaxy is configured to sit behind a proxy providing authentication
 * information - this cannot be used.
 *
 * @author John Chilton
 */
public class GalaxyAuthWebResourceFactoryImpl extends DefaultWebResourceFactoryImpl {
  private final String email;
  private final String password;
  private String key = null;
  
  public GalaxyAuthWebResourceFactoryImpl(final String url, final String email, final String password, boolean debug) {
    super(url, null, debug);
    this.email = email;
    this.password = password;
  }

  public GalaxyAuthWebResourceFactoryImpl(final String url, final String email, final String password) {
    super(url, null);
    this.email = email;
    this.password = password;
  }

  @Override
  public synchronized String getApiKey() {
    if(key == null) {
      final String unencodedCredentials = email + ":" + password;
      final String encodedCredentials = javax.xml.bind.DatatypeConverter.printBase64Binary(unencodedCredentials.getBytes());
      final WebResource resource = super.getRawWebResource();
      final ClientResponse response = resource.path("authenticate")
                                              .path("baseauth")
                                              .header("Authorization", encodedCredentials)
                                              .get(ClientResponse.class);
      if(response.getStatus() != 200) {
        throw new RuntimeException("Failed to build Galaxy API key for supplied user e-mail and password.");
      }
      final Map<String, Object> responseObjects = response.getEntity(Map.class);
      key = responseObjects.get("api_key").toString();
    }
    return key;
  }


  
}
