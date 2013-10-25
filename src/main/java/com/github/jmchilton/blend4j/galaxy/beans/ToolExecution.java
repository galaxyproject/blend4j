package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ToolExecution {
  private List<OutputDataset> outputs = new ArrayList<OutputDataset>();

  public List<OutputDataset> getOutputs() {
    return outputs;
  }

  public void setOutputs(List<OutputDataset> outputs) {
    this.outputs = outputs;
  }

}
