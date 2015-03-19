package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.DirectoryLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.GalaxyObject;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryFolder;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryPermissions;
import com.github.jmchilton.blend4j.galaxy.beans.UrlLibraryUpload;
import com.sun.jersey.api.client.ClientResponse;

import java.util.HashMap;
import java.util.Map;

class LibrariesClientImpl extends Client implements LibrariesClient {
  LibrariesClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "libraries");
  }

  public ClientResponse createLibraryRequest(final Library library) {
    return super.create(library);
  }

  public Library createLibrary(final Library library) {
    // should not be this complicated, I suspect library API is returning wrong thing. See galaxy issue #802
    return createLibraryRequest(library).getEntity(Library.class);
    //return readJson(createLibraryRequest(library).getEntity(String.class), new TypeReference<List<Library>>() {}).get(0);
  }
  
  public ClientResponse createFolderRequest(final String libraryId, final LibraryFolder folder) {
    final String baseFolderId = folder.getFolderId();
    if(baseFolderId == null) {
      throw new IllegalArgumentException("Must specify parent folder id");
    }
    return super.create(getWebResourceContents(libraryId), folder);
  }
  
  
  public LibraryFolder createFolder(final String libraryId, final LibraryFolder folder) {  
    return readJson(createFolderRequest(libraryId, folder).getEntity(String.class), new TypeReference<List<LibraryFolder>>() {}).get(0);
  }

  public List<Library> getLibraries() {
    return get(new TypeReference<List<Library>>() {
    });
  }

  public ClientResponse uploadFileFromUrlRequest(final String libraryId, final UrlLibraryUpload upload) {
    return super.create(getWebResourceContents(libraryId), upload);
  }

  public ClientResponse uploadServerDirectoryRequest(final String libraryId, final DirectoryLibraryUpload upload) {
    return super.create(getWebResourceContents(libraryId), upload);
  }

  public ClientResponse uploadFilesystemPathsRequest(final String libraryId, final FilesystemPathsLibraryUpload upload) {
    return super.create(getWebResourceContents(libraryId), upload);
  }
  
  public GalaxyObject uploadFilesystemPaths(final String libraryId, final FilesystemPathsLibraryUpload upload) {
    return readJson(uploadFilesystemPathsRequest(libraryId, upload).getEntity(String.class), new TypeReference<List<GalaxyObject>>() {}).get(0);
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
    return get(getWebResourceContents(libraryId), new TypeReference<List<LibraryContent>>() {
    });
  }

  public ClientResponse uploadFileFromUrl(final String libraryId, final FilesystemPathsLibraryUpload upload) {
    return super.create(getWebResourceContents(libraryId), upload);
  }

  public ClientResponse setLibraryPermissions(String libraryId, LibraryPermissions permissions) {
    final String payload = write(permissions);
    return super.create(getWebResource(libraryId).path("permissions"), payload);
  }

  public ClientResponse uploadFile(String libraryId, FileLibraryUpload upload) {
    final Map<String, Object> entityMap = new HashMap<String, Object>();
    entityMap.put("file_type", upload.getFileType());
    entityMap.put("db_key", upload.getDbkey());
    entityMap.put("files_0|NAME", upload.getName());
    entityMap.put("upload_option", upload.getUploadOption());
    entityMap.put("folder_id", upload.getFolderId());
    entityMap.put("create_type", upload.getCreateType());
    return super.multipartPost(getWebResourceContents(libraryId), entityMap, prepareUpload(upload.getFile()));
  }
  
  @Override
  public ClientResponse showDatasetRequest(String libraryId, String datasetId) {
    return getResponse(getWebResourceContents(libraryId).path(datasetId));
  }

  @Override
  public LibraryDataset showDataset(String libraryId, String datasetId) {
    return read(showDatasetRequest(libraryId, datasetId), LibraryDataset.class);
  }

  @Override
  public ClientResponse deleteLibraryRequest(String libraryId) {
    return deleteResponse(getWebResource(libraryId), "");
  }
}
