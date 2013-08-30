package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.RepositoryInstall;
import com.sun.jersey.api.client.ClientResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToolShedRepositoriesTest {
  private GalaxyInstance instance;
  private ToolShedRepositoriesClient reposClient;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    reposClient = instance.getRepositoriesClient();
  }

  
  @Test
  public void testInstall() {
    RepositoryInstall install = new RepositoryInstall();
    install.setChangsetRevision("a38cd98a6b41");
    install.setName("dbbuilder");
    install.setOwner("galaxyp");
    final ClientResponse response = reposClient.installRepositoryRequest(install);
    assert response.getStatus() == 200;
  }
  
  
}
