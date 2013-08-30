package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import java.util.List;

public interface RepositoriesClient {

  List<Repository> getRepositories();
  
  Repository showRepository(final String toolId);
  
}
