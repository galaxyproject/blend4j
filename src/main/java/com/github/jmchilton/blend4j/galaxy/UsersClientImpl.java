package com.github.jmchilton.blend4j.galaxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.User;
import com.github.jmchilton.blend4j.galaxy.beans.UserCreate;
import com.sun.jersey.api.client.ClientResponse;

class UsersClientImpl extends Client implements UsersClient {
  private static final TypeReference<List<User>> USER_LIST_TYPE_REFERENCE = new TypeReference<List<User>>() {
  };

  UsersClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "users");
  }

  public List<User> getUsers() {
    // Handles both User lists and single user retrieval
    try {
      return super.get(USER_LIST_TYPE_REFERENCE);
    } catch(RuntimeException e) {
      List out = new ArrayList();
      out.add(super.getWebResource().get(User.class));
      return out;
    }
  }

  public ClientResponse createUserRequest(final String remoteUserEmail) {
    final Map<String, String> map = new HashMap<String, String>();
    map.put("remote_user_email", remoteUserEmail);
    return super.getWebResource().post(ClientResponse.class, write(map));
  }

  public User createUser(final String remoteUserEmail) {
    return createUserRequest(remoteUserEmail).getEntity(User.class);
  }

  public User showUser(String id) {
    return super.show(id, User.class);
  }


  @Override
  public User createUser(UserCreate userCreate) {
    return createUserRequest(userCreate).getEntity(User.class);
  }

  @Override
  public String createApiKey(String userId) {
    return super.read(super.getWebResource().path(userId).path("api_key").post(ClientResponse.class), String.class);
  }

  @Override
  public ClientResponse createUserRequest(UserCreate userCreate) {
    return super.getWebResource().post(ClientResponse.class, write(userCreate));
  }

}
