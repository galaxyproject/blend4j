package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.User;
import com.github.jmchilton.blend4j.galaxy.beans.UserCreate;
import com.sun.jersey.api.client.ClientResponse;

public interface UsersClient {
  List<User> getUsers();

  ClientResponse createUserRequest(final String remoteUserEmail);

  User createUser(final String remoteUserEmail);
  
  User createUser(final UserCreate userCreate);

  String createApiKey(final String userId);
  
  User showUser(String id);
}
