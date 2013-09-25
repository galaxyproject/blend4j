package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.InstallableRepositoryRevision;
import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryInstall;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryWorkflow;
import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;

public interface ToolShedRepositoriesClient {

  List<InstalledRepository> getRepositories();
  
  InstalledRepository showRepository(String toolShedId);

  ClientResponse installRepositoryRequest(RepositoryInstall install);

  List<InstalledRepository> installRepository(final RepositoryInstall install);
  
  ClientResponse repairRepositoryRequest(InstallableRepositoryRevision repositoryIdentifier);

  ClientResponse exportedWorkflowsRequest(String toolShedId);
  
  List<RepositoryWorkflow> exportedWorkflows(String toolShedId);
  
  ClientResponse importWorkflowRequest(String toolShedId, int index);
  
  Workflow importWorkflow(String toolShedId, int index);
  
  ClientResponse importWorkflowsRequest(String toolShedId);
  
  List<Workflow> importWorkflows(String toolShedId);

}
