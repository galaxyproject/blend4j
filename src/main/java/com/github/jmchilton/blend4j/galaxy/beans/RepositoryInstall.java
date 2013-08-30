package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class RepositoryInstall {
  @JsonProperty("tool_shed_url")
  private String toolShedUrl = "http://toolshed.g2.bx.psu.edu/";
  private String name;
  private String owner;
  @JsonProperty("changeset_revision")
  private String changsetRevision;
  @JsonProperty("new_tool_panel_section_label")
  private String newToolPanelSectionLabel = "";
  @JsonProperty("tool_panel_section_id")
  private String toolPanelSectionId = "";
  @JsonProperty("install_repository_dependencies")
  private boolean installRepositoryDependencies = false;
  @JsonProperty("install_tool_dependencies")
  private boolean installToolDependencies = false;

  public String getToolShedUrl() {
    return toolShedUrl;
  }

  public void setToolShedUrl(String toolShedUrl) {
    this.toolShedUrl = toolShedUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getChangsetRevision() {
    return changsetRevision;
  }

  public void setChangsetRevision(String changsetRevision) {
    this.changsetRevision = changsetRevision;
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
