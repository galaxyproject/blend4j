package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.galaxy.WebResourceFactory;
import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;

public class RepositoriesClientImpl extends Client implements RepositoriesClient {
  private static final TypeReference<List<Repository>> TOOL_LIST_TYPE_REFERENCE = new TypeReference<List<Repository>>() {
  };

  RepositoriesClientImpl(WebResourceFactory webResourceFactory) {
    super(webResourceFactory, "repositories");
  }
  
  public List<Repository> getRepositories() {
    return super.get(TOOL_LIST_TYPE_REFERENCE);
  }

  public Repository showRepository(final String toolId) {
    return super.show(toolId, Repository.class);
  }

}
