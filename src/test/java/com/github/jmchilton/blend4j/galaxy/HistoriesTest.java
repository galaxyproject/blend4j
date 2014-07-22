package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.DatasetCollection;
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
  
  private File collectionFile1;
  private File collectionFile2;
  private OutputDataset collectionDataset1;
  private OutputDataset collectionDataset2;
  
  private String collectionHistoryId;

  @BeforeMethod
  public void init() throws InterruptedException {
    instance = TestGalaxyInstance.get();
    toolsClient = instance.getToolsClient();
    historiesClient = instance.getHistoriesClient();
    
    buildHistoryForDatasets();
  }
  
  private void buildHistoryForDatasets() throws InterruptedException {
    String historyName = "collectionsHistory";
    collectionHistoryId = TestHelpers.createTestHistory(instance, historyName);
    
    collectionFile1 = TestHelpers.getTestFile();
    collectionFile2 = TestHelpers.getTestFile();
    collectionDataset1 = TestHelpers.testUpload(instance, collectionHistoryId, collectionFile1);
    collectionDataset2 = TestHelpers.testUpload(instance, collectionHistoryId, collectionFile2);
    Assert.assertNotNull(collectionDataset1);
    Assert.assertNotNull(collectionDataset2);
    Assert.assertEquals(collectionDataset1.getName(), collectionFile1.getName());
    Assert.assertEquals(collectionDataset2.getName(), collectionFile2.getName());
    TestHelpers.waitForHistory(historiesClient, collectionHistoryId);
  }
  
  private String responseErrorMessage(ClientResponse response) {
	  return "Response is " + response .getStatus() + ", content="
		        + response.getEntity(String.class);
  }

  @Test
  public void testShowHistory() {
    //final HistoriesClient client = IntegrationTest.getHistoriesClient();
    // TODO:
  }
  
  /**
   * Tests out building a new list dataset collection successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListPass() throws InterruptedException {
	String collectionName = "testCreateDatasetCollectionListPass";
	  
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId());
    element1.setName(collectionDataset1.getName());
    element1.setSource("hda");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId());
    element2.setName(collectionDataset2.getName());
    element2.setSource("hda");
    
    DatasetCollectionDescription collectionDescription = new DatasetCollectionDescription();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    DatasetCollection collection = historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    Assert.assertEquals(collectionName, collection.getName());
    Assert.assertEquals("list", collection.getCollectionType());
    Assert.assertNotNull(collection.getId());
    
    List<DatasetCollectionElement> elements = collection.getElements();
    Assert.assertEquals(2, elements.size());
    
    DatasetCollectionElement responseElement1 = elements.get(0);
    Assert.assertEquals(0, responseElement1.getElementIndex());
    Assert.assertNotNull(responseElement1.getId());
    
    Dataset responseDataset1 = responseElement1.getDataset();
    Assert.assertEquals(collectionDataset1.getName(), responseDataset1.getName());
  }
  
  /**
   * Tests out building a new list dataset collection and failing.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListFail() throws InterruptedException {
	String collectionName = "testCreateDatasetCollectionListFail";
	  
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId());
    element1.setName(collectionDataset1.getName());
    element1.setSource("hda");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId() + "makeFailNow");
    element2.setName(collectionDataset2.getName());
    element2.setSource("hda");
    
    DatasetCollectionDescription collectionDescription = new DatasetCollectionDescription();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), response.getStatus(), 500);
  }
  
  /**
   * Tests out building a new paired dataset collection successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionPairedPass() throws InterruptedException {
	String collectionName = "testCreateDatasetCollectionPairedPass";
	  
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId());
    element1.setName("forward");
    element1.setSource("hda");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId());
    element2.setName("reverse");
    element2.setSource("hda");
    
    DatasetCollectionDescription collectionDescription = new DatasetCollectionDescription();
    collectionDescription.setCollectionType("paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), response.getStatus(), 200);
  }
  
  /**
   * Tests out building a new paired dataset collection and failing.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionPairedFail() throws InterruptedException {
	String collectionName = "testCreateDatasetCollectionPairedFail";
	  
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId());
    element1.setName("forward");
    element1.setSource("hda");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId() + "makeFailNow");
    element2.setName("reverse");
    element2.setSource("hda");
    
    DatasetCollectionDescription collectionDescription = new DatasetCollectionDescription();
    collectionDescription.setCollectionType("paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), response.getStatus(), 500);
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
