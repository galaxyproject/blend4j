package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.DirectoryLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryPermissions;
import com.github.jmchilton.blend4j.galaxy.beans.UrlLibraryUpload;
import com.sun.jersey.api.client.ClientResponse;

public interface LibrariesClient {

  ClientResponse createLibraryRequest(Library library);

  Library createLibrary(Library library);

  List<Library> getLibraries();

  ClientResponse uploadFileFromUrlRequest(String libraryId, UrlLibraryUpload upload);

  ClientResponse uploadServerDirectoryRequest(String libraryId, DirectoryLibraryUpload upload);

  ClientResponse uploadFilesystemPathsRequest(String libraryId, FilesystemPathsLibraryUpload upload);

  ClientResponse uploadFile(String libraryId, FileLibraryUpload upload);
  
  LibraryContent getRootFolder(String libraryId);

  List<LibraryContent> getLibraryContents(String libraryId);

  ClientResponse uploadFileFromUrl(String libraryId, FilesystemPathsLibraryUpload upload);
  
  ClientResponse setLibraryPermissions(String libraryId, LibraryPermissions permissions);

}