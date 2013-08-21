package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ToolExecution {
  private List<Dataset> outputs = new ArrayList<Dataset>();

  public List<Dataset> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<Dataset> outputs) {
    this.outputs = outputs;
  }


}
