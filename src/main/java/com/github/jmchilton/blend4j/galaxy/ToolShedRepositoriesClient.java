package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository;
import java.util.List;

public interface ToolShedRepositoriesClient {

  List<InstalledRepository> getRepositories();
  
  InstalledRepository showRepository(String toolShedId);

}
