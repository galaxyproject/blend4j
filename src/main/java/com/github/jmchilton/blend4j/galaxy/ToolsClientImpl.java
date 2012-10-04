package com.github.jmchilton.blend4j.galaxy;

import java.util.List;
import java.util.ArrayList;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;

class ToolsClientImpl extends ClientImpl implements ToolsClient {

  ToolsClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "tools");
  }

  public List<Dataset> create(History history, ToolInputs inputs) {
    inputs.setHistoryId(history.getId());
    super.create(inputs);
    // XXX Datasets not yet properly returned from Tool creation
    return new ArrayList();
  }
}
