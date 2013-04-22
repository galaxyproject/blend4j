package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.WebResource;

public interface GalaxyInstance {
  HistoriesClient getHistoriesClient();

  LibrariesClient getLibrariesClient();

  UsersClient getUsersClient();

  WorkflowsClient getWorkflowsClient();

  RolesClient getRolesClient();

  ToolsClient getToolsClient();

  WebResource getWebResource();

  String getGalaxyUrl();
}
