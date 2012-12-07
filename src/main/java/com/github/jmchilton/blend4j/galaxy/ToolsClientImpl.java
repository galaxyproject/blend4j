package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  
  public ClientResponse fileUploadRequest(final String historyId, 
                                          final String fileType, 
                                          final String dbKey, 
                                          final File file) {
    final Map<String, String> uploadParameters = new HashMap<String, String>();
    uploadParameters.put("dbkey", dbKey);
    uploadParameters.put("file_type", fileType);
    uploadParameters.put("files_0|NAME", file.getName());
    uploadParameters.put("files_0|type", "upload_dataset");
    final Map<String, Object> requestParameters = new HashMap<String, Object>();
    requestParameters.put("tool_id", "upload1");
    requestParameters.put("history_id", historyId);
    requestParameters.put("inputs", write(uploadParameters));
    requestParameters.put("type", "upload_dataset");
    return multipartPost(getWebResource(), requestParameters, prepareUpload(file));
  }
  
}
