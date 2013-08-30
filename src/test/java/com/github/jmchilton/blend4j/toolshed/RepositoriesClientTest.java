package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import com.github.jmchilton.blend4j.toolshed.beans.RepositoryDetails;
import com.github.jmchilton.blend4j.toolshed.beans.RepositoryRevision;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RepositoriesClientTest {
  private static final Repository DBBUILDER_REPOSITORY = new Repository("galaxyp", "dbbuilder");
  private ToolShedInstance instance;
  private RepositoriesClient reposClient;
  
  @BeforeMethod
  public void init() {
    instance = ToolShedInstanceFactory.getMainToolShedInstance();
    reposClient = instance.getRepositoriesClient();    
  }
  
  @Test
  public void testGetTools() {
    final List<RepositoryDetails> tools = reposClient.getRepositories();
    boolean found = false;
    for(final RepositoryDetails tool : tools) {
      if(tool.getName().equals("abyss_tool")) {
        found = true;
        break;
      }
    }
    assert found;
  }
  
  @Test
  public void testGetInstallableRevisions() {
    final List<String> revisions = reposClient.getInstallableRevisions(DBBUILDER_REPOSITORY);
    assert revisions.size() > 1;
    assert revisions.get(0).equals("abbd37e8bd14");
  }
  
  @Test
  public void testGetLatestRepositoryRevision() {
    final RepositoryRevision revision = RepositoryUtils.getLatestRepositoryRevision(reposClient, DBBUILDER_REPOSITORY);
    final ClientResponse response = reposClient.getRepositoryRevisionInstallInfoRequest(revision);
    assert response.getStatus() == 200 : response.getStatus();
  }
  
}
