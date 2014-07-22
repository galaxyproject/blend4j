package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollectionElement;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.sun.jersey.api.client.ClientResponse;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HistoriesTest {
  private GalaxyInstance instance;
  private ToolsClient toolsClient;
  private HistoriesClient historiesClient;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    toolsClient = instance.getToolsClient();
    historiesClient = instance.getHistoriesClient();
  }

  @Test
  public void testShowHistory() {
    //final HistoriesClient client = IntegrationTest.getHistoriesClient();
    // TODO:
  }
  
  /**
   * Tests out building a new dataset collection list successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListPass() throws InterruptedException {
    String historyName = "testCreateDatasetCollectionListPass";
    String collectionName = "collection1";
    final String historyId = TestHelpers.createTestHistory(instance, historyName);
    
    File file1 = TestHelpers.getTestFile();
    File file2 = TestHelpers.getTestFile();
    OutputDataset dataset1 = TestHelpers.testUpload(instance, historyId, file1);
    OutputDataset dataset2 = TestHelpers.testUpload(instance, historyId, file2);
    Assert.assertNotNull(dataset1);
    Assert.assertNotNull(dataset2);
    Assert.assertEquals(dataset1.getName(), file1.getName());
    Assert.assertEquals(dataset2.getName(), file2.getName());
    TestHelpers.waitForHistory(historiesClient, historyId);
    
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(dataset1.getId());
    element1.setName(file1.getName());
    element1.setSource("hda");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(dataset2.getId());
    element2.setName(dataset2.getName());
    element2.setSource("hda");
    
    DatasetCollectionDescription collectionDescription = new DatasetCollectionDescription();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    
    ClientResponse response = historiesClient.createDatasetCollection(historyId, collectionDescription);
    Assert.assertEquals("Response is " + response .getStatus() + ", content="
        + response.getEntity(String.class), response.getStatus(), 200);
  }
  
  @Test(expectedExceptions = RuntimeException.class)
  public void testShowDatasetCollectionFail() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final String fakeDatasetCollectionId = "fake";
    
    historiesClient.showDatasetCollection(historyId, fakeDatasetCollectionId);
  }
  
  @Test
  public void testImportExport() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final ToolsClient.FileUploadRequest request = new ToolsClient.FileUploadRequest(historyId, testFile);
    final ClientResponse clientResponse = toolsClient.uploadRequest(request);
    TestHelpers.waitForHistory(historiesClient, historyId);
    HistoryExport export = historiesClient.exportHistory(historyId);
    assert ! export.isReady();
    do {
      export = historiesClient.exportHistory(historyId);
    } while( ! export.isReady() );
  }
  
  @Test
  public void testProvenance() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final File testFile = TestHelpers.getTestFile();
    final ToolsClient.FileUploadRequest request = new ToolsClient.FileUploadRequest(historyId, testFile);
    final ClientResponse clientResponse = toolsClient.uploadRequest(request);
    
    final List<HistoryContents> contentsList = historiesClient.showHistoryContents(historyId);
    final HistoryContents contents = contentsList.get(0);
    final HistoryContentsProvenance prov = historiesClient.showProvenance(historyId, contents.getId());    
  }
  
}
