package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryIdentifier;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryInstall;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;

public interface ToolShedRepositoriesClient {

  List<InstalledRepository> getRepositories();
  
  InstalledRepository showRepository(String toolShedId);

  ClientResponse installRepositoryRequest(RepositoryInstall install);

  ClientResponse repairRepositoryRequest(RepositoryIdentifier repositoryIdentifier);

}
