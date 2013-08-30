package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.ToolShedRepository;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;

class ToolShedRepositoriesClientImpl extends Client implements ToolShedRepositoriesClient {
  private static final TypeReference<List<ToolShedRepository>> TOOL_SHED_REPOSITORY_LIST_TYPE_REFERENCE = new TypeReference<List<ToolShedRepository>>() {
  };

  ToolShedRepositoriesClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "tool_shed_repositories");
  }


  public List<ToolShedRepository> getRepositories() {
    return super.get(TOOL_SHED_REPOSITORY_LIST_TYPE_REFERENCE);
  }

  public ToolShedRepository showRepository(final String toolShedId) {
    return super.show(toolShedId, ToolShedRepository.class);
  }

}
