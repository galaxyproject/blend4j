package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import java.util.List;
import org.testng.annotations.Test;

public class RepositoriesClientTest {
  
  @Test
  public void testGetTools() {
    final ToolShedInstance instance = ToolShedInstanceFactory.getMainToolShedInstance();
    final RepositoriesClient reposClient = instance.getRepositoriesClient();
    final List<Repository> tools = reposClient.getRepositories();
    boolean found = false;
    for(final Repository tool : tools) {
      if(tool.getName().equals("abyss_tool")) {
        found = true;
        break;
      }
    }
    assert found;
  }
  
}
