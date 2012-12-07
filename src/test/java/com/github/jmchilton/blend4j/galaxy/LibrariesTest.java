package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.FilesystemPathsLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryUpload;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.util.UUID;
import org.testng.annotations.Test;

public class LibrariesTest {

  @Test
  public void testPathPaste() {
    final LibrariesClient client = IntegrationTest.getLibrariesClient();
    final Library testLibrary = IntegrationTest.createTestLibrary(client, "test-filesystem-paths" + UUID.randomUUID().toString());
    final LibraryContent rootFolder = client.getRootFolder(testLibrary.getId());
    final boolean composite = false;
    final boolean linkData = false;
    final FilesystemPathsLibraryUpload upload = new FilesystemPathsLibraryUpload(composite);
    upload.setName("MOOCOW");
    upload.setContent("test-data/visualization/phyloviz/");
    upload.setLinkData(linkData);
    upload.setFolderId(rootFolder.getId());
    upload.setFileType("tabular");
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
  
}
