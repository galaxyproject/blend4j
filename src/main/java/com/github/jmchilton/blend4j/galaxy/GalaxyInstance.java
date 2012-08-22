package com.github.jmchilton.blend4j.galaxy;

public interface GalaxyInstance {

  HistoriesClient getHistoriesClient();

  LibrariesClient getLibrariesClient();

  UsersClient getUsersClient();
  
  WorkflowsClient getWorkflowsClient();
  
  RolesClient getRolesClient();

}