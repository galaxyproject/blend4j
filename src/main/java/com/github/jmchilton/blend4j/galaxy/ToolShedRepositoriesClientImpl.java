package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository;
import java.util.List;
import org.codehaus.jackson.type.TypeReference;

class ToolShedRepositoriesClientImpl extends Client implements ToolShedRepositoriesClient {
  private static final TypeReference<List<InstalledRepository>> TOOL_SHED_REPOSITORY_LIST_TYPE_REFERENCE = new TypeReference<List<InstalledRepository>>() {
  };

  ToolShedRepositoriesClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "tool_shed_repositories");
  }


  public List<InstalledRepository> getRepositories() {
    return super.get(TOOL_SHED_REPOSITORY_LIST_TYPE_REFERENCE);
  }

  public InstalledRepository showRepository(final String toolShedId) {
    return super.show(toolShedId, InstalledRepository.class);
  }

}
