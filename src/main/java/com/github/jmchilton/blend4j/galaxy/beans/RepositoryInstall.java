package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class RepositoryInstall extends InstallableRepositoryRevision {
  @JsonProperty("new_tool_panel_section_label")
  private String newToolPanelSectionLabel = "";
  @JsonProperty("tool_panel_section_id")
  private String toolPanelSectionId = "";
  @JsonProperty("install_repository_dependencies")
  private boolean installRepositoryDependencies = false;
  @JsonProperty("install_tool_dependencies")
  private boolean installToolDependencies = false;

  public RepositoryInstall() {
    super();
  }
  
  public RepositoryInstall(final InstallableRepositoryRevision revision) {
    super(revision);
  }
  
  
  public String getNewToolPanelSectionLabel() {
    return newToolPanelSectionLabel;
  }

  public void setNewToolPanelSectionLabel(String newToolPanelSectionLabel) {
    this.newToolPanelSectionLabel = newToolPanelSectionLabel;
  }

  public String getToolPanelSectionId() {
    return toolPanelSectionId;
  }

  public void setToolPanelSectionId(String toolPanelSectionId) {
    this.toolPanelSectionId = toolPanelSectionId;
  }

  public boolean isInstallRepositoryDependencies() {
    return installRepositoryDependencies;
  }

  public void setInstallRepositoryDependencies(boolean installRepositoryDependencies) {
    this.installRepositoryDependencies = installRepositoryDependencies;
  }

  public boolean isInstallToolDependencies() {
    return installToolDependencies;
  }

  public void setInstallToolDependencies(boolean installToolDependencies) {
    this.installToolDependencies = installToolDependencies;
  }
  
}
