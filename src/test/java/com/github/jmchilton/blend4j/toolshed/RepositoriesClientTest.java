package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Tool;
import java.util.List;
import org.testng.annotations.Test;

public class RepositoriesClientTest {
  
  @Test
  public void testGetTools() {
    final ToolShedInstance instance = ToolShedInstanceFactory.getMainToolShedInstance();
    final RepositoriesClient reposClient = instance.getRepositoriesClient();
    final List<Tool> tools = reposClient.getTools();
    boolean found = false;
    for(final Tool tool : tools) {
      if(tool.getName().equals("abyss_tool")) {
        found = true;
        break;
      }
    }
    assert found;
  }
  
}
