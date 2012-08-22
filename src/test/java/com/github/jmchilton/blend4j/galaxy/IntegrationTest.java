package com.github.jmchilton.blend4j.galaxy;

import java.util.List;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryPermissions;
import com.github.jmchilton.blend4j.galaxy.beans.Role;
import com.github.jmchilton.blend4j.galaxy.beans.User;
import com.sun.jersey.api.client.ClientResponse;

public class IntegrationTest {

  @Test
  public void checkHistories() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final HistoriesClient historyClient = galaxyInstance.getHistoriesClient();
    final History history = new History();
    final String testHistoryName = "testHistory" + UUID.randomUUID().toString();
    history.setName(testHistoryName);
    final ClientResponse response = historyClient.createRequest(history);
    Assert.assertTrue(response.getStatus() == 200, String.format("Expected 200: %d", response.getStatus()));
    final List<History> histories = historyClient.getHistories();
    History foundHistory = null;
    for(final History returnedHistory : histories) {
      if(returnedHistory.getName().equals(testHistoryName)) {
        foundHistory = returnedHistory;
      }
    }
    Assert.assertTrue(foundHistory != null, "Could not find previously created test history.");
    final ClientResponse showResponse = historyClient.showHistoryRequest(foundHistory.getId());
    assert200(showResponse);
    final HistoryDetails historyDetails = historyClient.showHistory(foundHistory.getId());
    Assert.assertEquals(historyDetails.getState(), "new");
    final ClientResponse contentsResponse = historyClient.showHistoryContentsRequest(foundHistory.getId());
    assert200(contentsResponse);
    // System.out.println(contentsResponse.getEntity(String.class));
  }

  private void assert200(final ClientResponse clientResponse) {
    Assert.assertTrue(clientResponse.getStatus() == 200,
        String.format("Expected 200 status code, got %d. %s", clientResponse.getStatus(), clientResponse.getEntity(String.class)));
  }

  @Test
  public void testLibraries() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final LibrariesClient libraryClient = galaxyInstance.getLibrariesClient();
    final Library testLibrary = new Library();
    testLibrary.setName("testLib" + UUID.randomUUID().toString());
    final ClientResponse response = libraryClient.createLibraryRequest(testLibrary);
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
    upload.setContent("test-data/1.bam");
    upload.setFolderId(rootFolderId);
    final ClientResponse uploadResponse = libraryClient.uploadFileFromUrl(matchingLibrary.getId(), upload);
    assert200(uploadResponse);
  }

  @Test
  public void testUsers() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final UsersClient usersClient = galaxyInstance.getUsersClient();
    final List<User> users = usersClient.getUsers();
    for(final User user : users) {
      usersClient.showUser(user.getId());
    }
    ClientResponse response = usersClient.createUserRequest(UUID.randomUUID() + "@userexample.com");
    assert200(response);
  }

  @Test
  public void testWorkflows() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final WorkflowsClient workflowsClient = galaxyInstance.getWorkflowsClient();
    workflowsClient.getWorkflows();
  }

  @Test
  public void testCreatePrivateDataLibrary() {
    final GalaxyInstance galaxyInstance = TestGalaxyInstance.get();
    final UsersClient usersClient = galaxyInstance.getUsersClient();
    final String email = UUID.randomUUID().toString() + "@createprivatelibraryexample.com";
    final ClientResponse response = usersClient.createUserRequest(email);
    assert200(response);
    final Role role = galaxyInstance.getRolesClient().getRole(email);
    final Library library = new Library();
    library.setName("DataImport-" + email);
    final LibrariesClient libraryClient = galaxyInstance.getLibrariesClient();
    final Library createdLibrary = libraryClient.createLibrary(library);
    final LibraryPermissions libraryPermissions = new LibraryPermissions();
    libraryPermissions.getAccessInRoles().add(role.getId());
    final ClientResponse setPermResponse = libraryClient.setLibraryPermissions(createdLibrary.getId(), libraryPermissions);
    assert200(setPermResponse);
  }

}
