package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryDataset;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryFolder;
import com.sun.jersey.api.client.ClientResponse;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LibrariesTest {
  @Test
  public void testCreateFolder() {
    final LibrariesClient client = IntegrationTest.getLibrariesClient();
    final Library testLibrary = IntegrationTest.createTestLibrary(client, "test-filesystem-paths" + UUID.randomUUID().toString());
    final LibraryContent rootFolder = client.getRootFolder(testLibrary.getId());
    final LibraryFolder folder = new LibraryFolder();
    folder.setDescription("Folder Descriptions");
    folder.setName("Folder Name");
    folder.setFolderId(rootFolder.getId());

    final ClientResponse result = client.createFolderRequest(testLibrary.getId(), folder);
    assert result.getStatus() == 200 : result.getEntity(String.class);
    assert folder.getId() == null;

    final LibraryFolder resultFolder = client.createFolder(testLibrary.getId(), folder);
    assert resultFolder.getName().equals("Folder Name");
    assert resultFolder.getId() != null;
  }
  
  
  @Test
  public void testPathPaste() {
    final LibrariesClient client = IntegrationTest.getLibrariesClient();
    final Library testLibrary = IntegrationTest.createTestLibrary(client, "test-filesystem-paths" + UUID.randomUUID().toString());
    final LibraryContent rootFolder = client.getRootFolder(testLibrary.getId());
    final boolean composite = false;
    final boolean linkData = false;
    final FilesystemPathsLibraryUpload upload = new FilesystemPathsLibraryUpload(composite);
    upload.setName("MOOCOW");
    upload.setContent("test-data/users/test1@bx.psu.edu/");
    upload.setLinkData(linkData);
    upload.setFolderId(rootFolder.getId());
    upload.setFileType("fasta");
    final ClientResponse uploadResponse = client.uploadFileFromUrl(testLibrary.getId(), upload);
    IntegrationTest.assert200(uploadResponse);
  }

  @Test
  public void testFileUpload() {
    final File testFile = TestHelpers.getTestFile();
    final LibrariesClient client = IntegrationTest.getLibrariesClient();
    final Library testLibrary = IntegrationTest.createTestLibrary(client, "test-filesystem-paths" + UUID.randomUUID().toString());
    final LibraryContent rootFolder = client.getRootFolder(testLibrary.getId());
    final FileLibraryUpload upload = new FileLibraryUpload();
    upload.setName("MOOCOWFILE");
    upload.setFolderId(rootFolder.getId());
    upload.setFileType("tabular");
    upload.setFile(testFile);
    final ClientResponse uploadResponse = client.uploadFile(testLibrary.getId(), upload);
    IntegrationTest.assert200(uploadResponse);
  }
  
  /**
   * Tests to make sure we can successfully get a LibraryDataset object.
   */
  @Test
  public void testShowDatasetSuccess() {
    final File testFile = TestHelpers.getTestFile("Hello World\n");
    String fileName = testFile.getName();
    String libraryFilePathName = "/" + fileName;
    
    final LibrariesClient client = IntegrationTest.getLibrariesClient();
    final Library testLibrary = IntegrationTest.createTestLibrary(client, "test-show-dataset-success" + UUID.randomUUID().toString());
    final LibraryContent rootFolder = client.getRootFolder(testLibrary.getId());
    final FileLibraryUpload upload = new FileLibraryUpload();
    upload.setName(fileName);
    upload.setFolderId(rootFolder.getId());
    upload.setFileType("tabular");
    upload.setFile(testFile);
    client.uploadFile(testLibrary.getId(), upload);
    
    String datasetId = null;
    List<LibraryContent> libraryContents = client.getLibraryContents(testLibrary.getId());
    for (LibraryContent content : libraryContents) {
      if (libraryFilePathName.equals(content.getName())) {
        datasetId = content.getId();
      }
    }

    if (datasetId == null) {
      Assert.fail("Could not find dataset within library " + testLibrary.getId() +
          " corresponding to file " + fileName);
    } else {
      LibraryDataset libraryDataset = client.showDataset(testLibrary.getId(), datasetId);
      Assert.assertNotNull(libraryDataset);
      Assert.assertEquals(datasetId, libraryDataset.getId());
      Assert.assertEquals(fileName, libraryDataset.getName());
      Assert.assertEquals("tabular", libraryDataset.getDataType());
      Assert.assertEquals("tabular", libraryDataset.getDataTypeExt());
      Assert.assertNotNull(libraryDataset.getDataTypeClass());
      Assert.assertNotNull(libraryDataset.getState());
    }
  }
  
  /**
   * Tests deleting a library successfully.
   */
  @Test
  public void testDeleteLibrarySuccess() {
    LibrariesClient client = IntegrationTest.getLibrariesClient();
    Library testLibrary = IntegrationTest.createTestLibrary(client, "test-delete-library" + UUID.randomUUID().toString());
    assert testLibrary != null : "Library could not be created";
    assert client.getLibraryContents(testLibrary.getId()) != null : "Invalid library contents";
    
    ClientResponse clientResponse = client.deleteLibraryResponse(testLibrary.getId());
    assert 200 == clientResponse.getStatus() : "Invalid status code";
  }
  
  /**
   * Tests deleting a library and failing.
   */
  @Test
  public void testDeleteLibraryFail() {
    LibrariesClient client = IntegrationTest.getLibrariesClient();
    
    try {
      client.deleteLibraryResponse("invalid");
    } catch (GalaxyResponseException e) {
      assert 400 == e.getStatusCode() : "Invalid status code";
    }
  }
}
