package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.type.TypeReference;

import com.sun.jersey.api.client.ClientResponse;

public class LibraryClient extends Client {

  public LibraryClient(GalaxyInstance galaxyInstance) {
    super(galaxyInstance, "libraries");
  }

  public ClientResponse createRequest(final Library library) {
    return super.create(library);
  }

  public Library create(final Library library) {
    return createRequest(library).getEntity(Library.class);
  }

  public List<Library> getLibraries() {
    return get(new TypeReference<List<Library>>() {
    });
  }

  public LibraryContent getRootFolder(final String libraryId) {
    final List<LibraryContent> libraryContents = getLibraryContents(libraryId);
    LibraryContent rootFolder = null;
    for(final LibraryContent content : libraryContents) {
      if("/".equals(content.getName())) {
        rootFolder = content;
        break;
      }
    }
    return rootFolder;
  }

  public List<LibraryContent> getLibraryContents(final String libraryId) {
    return get(getWebResource().path(libraryId).path("contents"), new TypeReference<List<LibraryContent>>() {
    });
  }

  public ClientResponse uploadFileFromUrl(final String libraryId, final FilesystemPathsLibraryUpload upload) {
    return super.create(getWebResource().path(libraryId).path("contents"), upload);
  }

  public static class LibraryContent extends GalaxyObject {
    private String name;
    private String type;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

  }

  public static class Library extends GalaxyObject {
    private String name = "";
    private String description = "";
    private String synopsis = "";

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getSynopsis() {
      return synopsis;
    }

    public void setSynopsis(String synopsis) {
      this.synopsis = synopsis;
    }

  }

  public static class FilesystemPathsLibraryUpload extends LibraryUpload {
    private String fileUrl;

    @JsonGetter("upload_option")
    public String getUploadOption() {
      return "upload_paths";
    }

    @JsonGetter("filesystem_paths")
    public String getFilesystemPaths() {
      return fileUrl;
    }

    public void setFilesystemPaths(String fileUrl) {
      this.fileUrl = fileUrl;
    }

  }

  public static class LibraryUpload extends GalaxyObject {
    private String folderId;
    private String libraryId;
    private String fileType = "auto";
    private String dbkey = "?";

    @JsonGetter("folder_id")
    public String getFolderId() {
      return folderId;
    }

    public void setFolderId(String folderId) {
      this.folderId = folderId;
    }

    @JsonGetter("file_type")
    public String getFileType() {
      return fileType;
    }

    public void setFileType(String fileType) {
      this.fileType = fileType;
    }

    public String getDbkey() {
      return dbkey;
    }

    public void setDbkey(String dbkey) {
      this.dbkey = dbkey;
    }

    @JsonGetter("create_type")
    public String getCreateType() {
      return "file";
    }

  }

}
