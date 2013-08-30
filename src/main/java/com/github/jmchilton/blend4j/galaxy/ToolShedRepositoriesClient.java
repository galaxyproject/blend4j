package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.ToolShedRepository;
import java.util.List;

public interface ToolShedRepositoriesClient {

  List<ToolShedRepository> getRepositories();
  
  ToolShedRepository showRepository(String toolShedId);

}
