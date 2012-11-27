package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.List;

public interface ToolsClient {

  List<Dataset> create(History history, ToolInputs inputs);

  ClientResponse fileUploadRequest(String historyId, 
                                   String fileType, 
                                   String dbKey, 
                                   File file);

}
