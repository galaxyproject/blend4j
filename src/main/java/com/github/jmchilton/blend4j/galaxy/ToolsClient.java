package com.github.jmchilton.blend4j.galaxy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolDetails;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.github.jmchilton.blend4j.galaxy.beans.ToolSection;
import com.sun.jersey.api.client.ClientResponse;

public interface ToolsClient {
  ToolExecution create(History history, ToolInputs inputs);
  
  /**
   * 
   * @deprecated Use {@link uploadRequest} now.
   * 
   */
  @Deprecated
  ClientResponse fileUploadRequest(String historyId,
                                   String fileType,
                                   String dbKey,
                                   File file);
  
  ClientResponse uploadRequest(FileUploadRequest request);
  
  ToolExecution upload(FileUploadRequest request);
  
  /**
   * Load details about a specific Galaxy tool by ID.
   * 
   * @param toolId Galaxy tool ID (usually in the form of a URI, unlike other 
   * 		Galaxy IDs).
   * @return An instance of {@link ToolDetails} for the specified ID.
   */
  ToolDetails showTool(String toolId);
  
  /**
   * Loads a collection of all tools in the Galaxy instance, sorted by
   * section as in the tools panel. For more details about individual
   * tools, load {@link ToolDetails} with 
   * {@link ToolsClient#showTool(String)}.
   * 
   * @return a collection of all tools in the Galaxy instance.
   */
  List<ToolSection> getTools();
  
  public static class UploadFile {
    private final File file;
    private final String name;
    
    public UploadFile(final File file) {
      this(file, file.getName());
    }
    
    public UploadFile(final File file, final String name) {
      this.file = file;
      this.name =name;
    }
    
    public File getFile() {
      return file;
    }
    
    public String getName() {
      return name;
    }
    
  }
  
  public static class FileUploadRequest {
    private final String historyId;
    private final Iterable<UploadFile> files;
    private String fileType = "auto";
    private String dbKey = "?";
    private String toolId = "upload1";
    // Specify datasetName instead of file name, useful for multiple file uploads.
    private String datasetName = null;
    private Map<String, String> extraParameters = new HashMap<String, String>();
    
    public Map<String, String> getExtraParameters() {
      return extraParameters;
    }
    
    public void setExtraParameters(final Map<String, String> extraParameters) {
      this.extraParameters = extraParameters;
    }
    
    private static Iterable<UploadFile> convertFiles(final Iterable<File> files) {
      final List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
      for(final File file : files) {
        uploadFiles.add(new UploadFile(file));
      }
      return uploadFiles;
    }
    
    public FileUploadRequest(final String historyId, final File file) {
      this(historyId, convertFiles(Arrays.asList(file)));
    }
    
    public FileUploadRequest(final String historyId, final UploadFile file) {
      this(historyId, Arrays.asList(file));
    }
    
    public FileUploadRequest(final String historyId, final Iterable<UploadFile> files) {
      this.historyId = historyId;
      this.files = files;
    }
    
    public String getFileType() {
      return fileType;
    }

    public void setFileType(String fileType) {
      this.fileType = fileType;
    }

    public String getDbKey() {
      return dbKey;
    }

    public String getDatasetName() {
      return datasetName;
    }

    public void setDatasetName(String datasetName) {
      this.datasetName = datasetName;
    }

    public void setDbKey(String dbKey) {
      this.dbKey = dbKey;
    }

    public String getToolId() {
      return toolId;
    }

    public String getHistoryId() {
      return historyId;
    }

    public Iterable<UploadFile> getFiles() {
      return files;
    }
    
    public Iterable<File> getFileObjects() {
      final List<File> files = new ArrayList<File>();
      for(final UploadFile uploadFile : getFiles()) {
        files.add(uploadFile.getFile());
      }
      return files;
      
    }

    public void setToolId(String toolId) {
      this.toolId = toolId;
    }

  }
  
}
