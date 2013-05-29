package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.ToolInputs;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ToolsClient {
  List<Dataset> create(History history, ToolInputs inputs);

  
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
