package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.User;
import com.github.jmchilton.blend4j.galaxy.beans.UserCreate;
import com.sun.jersey.api.client.ClientResponse;
import java.util.UUID;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserTest {
  private final String TEST_PASSWORD = "testpass";
  private GalaxyInstance instance;
  private UsersClient client;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    client = instance.getUsersClient();
  }
  
  @Test
  public void testUserCreate() {
    final User user = createAndCheckTestUser();
    final String apiKey = client.createApiKey(user.getId());
  }
  
  @Test
  public void testInstanceFromCredentials() {
    final User user = createAndCheckTestUser();
    final String url = TestGalaxyInstance.getTestInstanceUrl();
    final GalaxyInstance authedInstance = GalaxyInstanceFactory.getFromCredentials(url, user.getEmail(), TEST_PASSWORD);
    final History history = new History();
    history.setName("NewUserHistory");
    final ClientResponse response = authedInstance.getHistoriesClient().createRequest(history);
    assert response.getStatus() == 200;
  }
  
  protected User createAndCheckTestUser() {
    final UserCreate userCreate = new UserCreate();
    final String username = UUID.randomUUID().toString();
    final String email = username + "@example.com";
    userCreate.setEmail(email);
    userCreate.setUsername(username);
    userCreate.setPassword(TEST_PASSWORD);
    
    final User user = client.createUser(userCreate);
    assert user.getEmail().equals(email);
    assert user.getUsername().equals(username);
    return user;
  }

}
