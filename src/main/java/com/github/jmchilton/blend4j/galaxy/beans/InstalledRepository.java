package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;


public class InstalledRepository extends GalaxyObject {
  @JsonProperty("changeset_revision")
  private String changesetRevision;
  @JsonProperty("ctx_rev")
  private int contextRevision;
  private boolean deleted;
  @JsonProperty("dist_to_shed")
  private boolean distToShed;
  @JsonProperty("error_message")
  private String errorMessage;
  @JsonProperty("includes_datatypes")
  private boolean includedDatatypes;
  @JsonProperty("installed_changeset_revision")
  private String installedChangesetRevision;
  private String name;
  private String owner;
  private String status;
  @JsonProperty("tool_shed")
  private String toolShed;
  private boolean uninstalled;
  @JsonProperty("update_avaiable")
  private boolean updateAvailable;
  
  public static enum InstallationStatus {
    NEW("New", false),
    CLONING("Cloning", false),
    SETTING_TOOL_VERSIONS("Setting tool versions", false),
    INSTALLING_REPOSITORY_DEPENDENCIES("Installing repository dependencies", false),
    INSTALLING_TOOL_DEPENDENCIES("Installing tool dependencies", false),
    LOADING_PROPRIETARY_DATATYPES("Loading proprietary datatypes", false),
    INSTALLED("Installed"),
    DEACTIVATED("Deactivated"),
    ERROR("Error"),
    UNINSTALLED("Uninstalled");

    private final String text;
    private final boolean complete;
    
    private InstallationStatus(final String text) {
      this(text, true);
    }

    private InstallationStatus(final String text, final boolean complete) {
      this.text = text;
      this.complete = complete;
    }

    public boolean isComplete() {
      return complete;
    }
    
    public static InstallationStatus fromText(final String text) {
      InstallationStatus targetStatus = null;
      for(InstallationStatus status : values()) {
        if(status.text.equals(text)) {
          targetStatus = status;
          break;
        }
      }
      return targetStatus;
    }
    
  }

  public String getChangesetRevision() {
    return changesetRevision;
  }

  public void setChangesetRevision(String changesetRevision) {
    this.changesetRevision = changesetRevision;
  }

  public int getContextRevision() {
    return contextRevision;
  }

  public void setContextRevision(int contextRevision) {
    this.contextRevision = contextRevision;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isDistToShed() {
    return distToShed;
  }

  public void setDistToShed(boolean distToShed) {
    this.distToShed = distToShed;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public boolean isIncludedDatatypes() {
    return includedDatatypes;
  }

  public void setIncludedDatatypes(boolean includedDatatypes) {
    this.includedDatatypes = includedDatatypes;
  }

  public String getInstalledChangesetRevision() {
    return installedChangesetRevision;
  }

  public void setInstalledChangesetRevision(String installedChangesetRevision) {
    this.installedChangesetRevision = installedChangesetRevision;
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
  
  public InstallationStatus getInstallationStatus() {
    return InstallationStatus.fromText(status);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getToolShed() {
    return toolShed;
  }

  public void setToolShed(String toolShed) {
    this.toolShed = toolShed;
  }

  public boolean isUninstalled() {
    return uninstalled;
  }

  public void setUninstalled(boolean uninstalled) {
    this.uninstalled = uninstalled;
  }

  public boolean isUpdateAvailable() {
    return updateAvailable;
  }

  public void setUpdateAvailable(boolean updateAvailable) {
    this.updateAvailable = updateAvailable;
  }

}
