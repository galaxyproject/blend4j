package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

class ToolsClientImpl extends ClientImpl implements ToolsClient {
  ToolsClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "tools");
  }

  public ToolExecution create(History history, ToolInputs inputs) {
    inputs.setHistoryId(history.getId());
    return super.create(inputs).getEntity(ToolExecution.class);
    // XXX Datasets not yet properly returned from Tool creation
    // return new ArrayList();
  }
  
  public ClientResponse fileUploadRequest(final String historyId,
                                          final String fileType,
                                          final String dbKey,
                                          final File file) {
    final FileUploadRequest request = new FileUploadRequest(historyId, file);
    request.setFileType(fileType);
    request.setDbKey(dbKey);
    return uploadRequest(request);
  }

  public ToolExecution upload(final FileUploadRequest request) {
    return uploadRequest(request).getEntity(ToolExecution.class);
  }
  
  public ClientResponse uploadRequest(final FileUploadRequest request) {
    final Map<String, String> uploadParameters = new HashMap<String, String>();
    final String datasetName = request.getDatasetName();
    if(datasetName != null) {
      uploadParameters.put("files_0|NAME", datasetName);
    } else {
      uploadParameters.put("files_0|NAME", request.getFiles().iterator().next().getName());
    }
    uploadParameters.put("dbkey", request.getDbKey());
    uploadParameters.put("file_type", request.getFileType());
    uploadParameters.putAll(request.getExtraParameters());
    final Map<String, Object> requestParameters = new HashMap<String, Object>();
    requestParameters.put("tool_id", request.getToolId());
    requestParameters.put("history_id", request.getHistoryId());
    requestParameters.put("inputs", write(uploadParameters));
    requestParameters.put("type", "upload_dataset");
    return multipartPost(getWebResource(), requestParameters, prepareUploads(request.getFileObjects()));
  }

}
