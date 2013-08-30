package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.galaxy.WebResourceFactory;
import com.github.jmchilton.blend4j.toolshed.beans.Tool;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;

public class RepositoriesClientImpl extends Client implements RepositoriesClient {
  private static final TypeReference<List<Tool>> TOOL_LIST_TYPE_REFERENCE = new TypeReference<List<Tool>>() {
  };

  RepositoriesClientImpl(WebResourceFactory webResourceFactory) {
    super(webResourceFactory, "repositories");
  }
  
  public List<Tool> getTools() {
    return super.get(TOOL_LIST_TYPE_REFERENCE);
  }

  public Tool showTool(final String toolId) {
    return super.show(toolId, Tool.class);
  }

}
