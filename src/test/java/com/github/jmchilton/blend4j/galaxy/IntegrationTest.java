package com.github.jmchilton.blend4j.galaxy;

import java.util.List;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.HistoryClient.History;
import com.github.jmchilton.blend4j.galaxy.LibraryClient.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.LibraryClient.Library;
import com.github.jmchilton.blend4j.galaxy.LibraryClient.LibraryContent;
import com.sun.jersey.api.client.ClientResponse;

public class IntegrationTest {

  @Test
  public void checkHistories() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final HistoryClient historyClient = galaxyInstance.getHistoryClient();
    final History history = new History();
    final String testHistoryName = "testHistory" + UUID.randomUUID().toString();
    history.setName(testHistoryName);
    final ClientResponse response = historyClient.createRequest(history);
    Assert.assertTrue(response.getStatus() == 200, String.format("Expected 200: %d", response.getStatus()));
    final Library persistedLibrary = response.getEntity(Library.class);
    System.out.println(persistedLibrary.getId());
    final List<History> histories = historyClient.getHistories();
    boolean foundHistory = false;
    for(final History returnedHistory : histories) {
      if(returnedHistory.getName().equals(testHistoryName)) {
        foundHistory = true;
      }
    }
    Assert.assertTrue(foundHistory, "Could not find previously created test history.");
  }

  private void assert200(final ClientResponse clientResponse) {
    Assert.assertTrue(clientResponse.getStatus() == 200,
        String.format("Expected 200 status code, got %d. %s", clientResponse.getStatus(), clientResponse.getEntity(String.class)));
  }

  @Test
  public void testLibraries() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final LibraryClient libraryClient = galaxyInstance.getLibraryClient();
    final Library testLibrary = new Library();
    testLibrary.setName("testLib" + UUID.randomUUID().toString());
    final ClientResponse response = libraryClient.createRequest(testLibrary);
    assert200(response);
    final List<Library> libraries = libraryClient.getLibraries();
    Library matchingLibrary = null;
    for(final Library library : libraries) {
      if(library.getName().equals(testLibrary.getName())) {
        matchingLibrary = library;
      }
    }
    Assert.assertTrue(matchingLibrary != null);
    final List<LibraryContent> contents = libraryClient.getLibraryContents(matchingLibrary.getId());
    final LibraryContent rootFolderContent = contents.get(0);
    final String rootFolderId = rootFolderContent.getId();
    final FilesystemPathsLibraryUpload upload = new FilesystemPathsLibraryUpload();
    upload.setFilesystemPaths("test-data/1.bam");
    upload.setFolderId(rootFolderId);
    final ClientResponse uploadResponse = libraryClient.uploadFileFromUrl(matchingLibrary.getId(), upload);
    assert200(uploadResponse);

    // for(final LibraryContent content : contents) {
    // System.out.println(content);
    // }
  }

}
