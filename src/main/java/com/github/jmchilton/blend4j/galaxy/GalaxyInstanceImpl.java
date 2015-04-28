package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.WebResource;

class GalaxyInstanceImpl implements GalaxyInstance {
  private final WebResourceFactory webResourceFactory;

  GalaxyInstanceImpl(final WebResourceFactory webResourceFactory) {
    this.webResourceFactory = webResourceFactory;
  }
  
  public ToolShedRepositoriesClient getRepositoriesClient() {
    return new ToolShedRepositoriesClientImpl(this);
  }

  public HistoriesClient getHistoriesClient() {
    return new HistoriesClientImpl(this);
  }

  public LibrariesClient getLibrariesClient() {
    return new LibrariesClientImpl(this);
  }

  public UsersClient getUsersClient() {
    return new UsersClientImpl(this);
  }

  public WorkflowsClient getWorkflowsClient() {
    return new WorkflowsClientImpl(this);
  }

  public RolesClient getRolesClient() {
    return new RolesClientImpl(this);
  }

  public ToolsClient getToolsClient() {
    return new ToolsClientImpl(this);
  }
  
  public ConfigurationClient getConfigurationClient() {
    return new ConfigurationClientImpl(this);
  }
  
  public SearchClient getSearchClient() {
    return new SearchClientImpl(this);
  }

  public WebResource getWebResource() {
    return webResourceFactory.get();
  }

  public String getGalaxyUrl() {
    return webResourceFactory.getGalaxyUrl();
  }
  
  public String getApiKey() {
    return webResourceFactory.getApiKey();
  }

  /**
   * {@inheritDoc}
   */
  public JobsClient getJobsClient() {
	  return new JobsClientImpl(this);
  }
}
