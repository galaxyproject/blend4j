
package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author John Chilton
 */
public class ToolShedRepository extends GalaxyObject {
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

}
