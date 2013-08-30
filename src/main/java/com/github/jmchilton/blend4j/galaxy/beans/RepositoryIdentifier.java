package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class RepositoryIdentifier {
  @JsonProperty(value="changeset_revision")
  protected String changsetRevision;
  protected String name;
  protected String owner;
  @JsonProperty(value="tool_shed_url")
  protected String toolShedUrl = "http://toolshed.g2.bx.psu.edu/";

  public String getChangsetRevision() {
    return changsetRevision;
  }

  public String getName() {
    return name;
  }

  public String getOwner() {
    return owner;
  }

  public String getToolShedUrl() {
    return toolShedUrl;
  }

  public void setChangsetRevision(String changsetRevision) {
    this.changsetRevision = changsetRevision;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setToolShedUrl(String toolShedUrl) {
    this.toolShedUrl = toolShedUrl;
  }

}
