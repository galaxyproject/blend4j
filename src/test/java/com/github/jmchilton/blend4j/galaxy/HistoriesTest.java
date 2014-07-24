package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionRequest;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionElementRequest;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.DatasetCollectionRequest;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.HistoryDatasetElementRequest;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.DatasetCollectionResponse;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.ResponseObject;
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
  
  /**
   * Checks to make sure the given dataset collection response matches the collection description.
   * @param collectionResponse  The DatasetCollection response from the server.
   * @param collectionDescription  The description of the dataset collection to send to the server.
   */
  private void assertDatasetCollectionResponseValid(DatasetCollectionResponse collectionResponse, 
    DatasetCollectionRequest collectionDescription) {
    
    Assert.assertEquals(collectionDescription.getName(), collectionResponse.getName());
    Assert.assertEquals(collectionDescription.getCollectionType(), collectionResponse.getCollectionType());
    Assert.assertNotNull(collectionResponse.getId());
    
    List<CollectionResponse> elementsResponse = collectionResponse.getElements();
    List<CollectionRequest> elementsDescription = collectionDescription.getDatasetElements();
    
    Assert.assertEquals(elementsDescription.size(), elementsResponse.size());
    
    for (int i = 0; i < elementsDescription.size(); i++) {
      CollectionRequest elementDescription = elementsDescription.get(i);
      CollectionResponse elementResponse = elementsResponse.get(i);
      
      Assert.assertEquals(i, elementResponse.getElementIndex());
      Assert.assertNotNull(elementResponse.getId());
      
      Assert.assertEquals(elementDescription.getName(), elementResponse.getElementIdentifier());
    }
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
	  
    HistoryDatasetElementRequest element1 = new HistoryDatasetElementRequest();
    element1.setId(collectionDataset1.getId());
    element1.setName(collectionDataset1.getName());
    
    HistoryDatasetElementRequest element2 = new HistoryDatasetElementRequest();
    element2.setId(collectionDataset2.getId());
    element2.setName(collectionDataset2.getName());
    
    DatasetCollectionRequest collectionDescription = new DatasetCollectionRequest();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    DatasetCollectionResponse collection = historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    assertDatasetCollectionResponseValid(collection, collectionDescription);
  }
  
  /**
   * Tests out building a new list dataset collection and failing.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionListFail";
	  
    HistoryDatasetElementRequest element1 = new HistoryDatasetElementRequest();
    element1.setId(collectionDataset1.getId());
    element1.setName(collectionDataset1.getName());
    
    HistoryDatasetElementRequest element2 = new HistoryDatasetElementRequest();
    element2.setId(collectionDataset2.getId() + "makeFailNow");
    element2.setName(collectionDataset2.getName());
    
    DatasetCollectionRequest collectionDescription = new DatasetCollectionRequest();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), 500, response.getStatus());
  }
  
  /**
   * Tests out building a new paired dataset collection successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionPairedPass() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionPairedPass";
	  
    HistoryDatasetElementRequest element1 = new HistoryDatasetElementRequest();
    element1.setId(collectionDataset1.getId());
    element1.setName("forward");
    
    HistoryDatasetElementRequest element2 = new HistoryDatasetElementRequest();
    element2.setId(collectionDataset2.getId());
    element2.setName("reverse");
    
    DatasetCollectionRequest collectionDescription = new DatasetCollectionRequest();
    collectionDescription.setCollectionType("paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    DatasetCollectionResponse collection = historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    assertDatasetCollectionResponseValid(collection, collectionDescription);
  }
  
  private DatasetCollectionRequest buildDatasetCollectionListPairedRequest(String collectionName, boolean fail) {

    HistoryDatasetElementRequest element1 = new HistoryDatasetElementRequest();
    element1.setId(collectionDataset1.getId() + (fail ? "fail" : ""));
    element1.setName("forward");
    
    HistoryDatasetElementRequest element2 = new HistoryDatasetElementRequest();
    element2.setId(collectionDataset2.getId());
    element2.setName("reverse");
    
    CollectionElementRequest pairedSet1 = new CollectionElementRequest();
    pairedSet1.setName("paired1");
    pairedSet1.setCollectionType("paired");
    pairedSet1.addCollectionElement(element1);
    pairedSet1.addCollectionElement(element2);
    
    CollectionElementRequest pairedSet2 = new CollectionElementRequest();
    pairedSet2.setName("paired2");
    pairedSet2.setCollectionType("paired");
    pairedSet2.addCollectionElement(element1);
    pairedSet2.addCollectionElement(element2);
    
    DatasetCollectionRequest collectionDescription = new DatasetCollectionRequest();
    collectionDescription.setCollectionType("list:paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(pairedSet1);
    collectionDescription.addDatasetElement(pairedSet2);
    
    return collectionDescription;
  }
  
  /**
   * Tests out building a new list of paired dataset collections successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListPairedPass() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionListPairedPass";
    
    DatasetCollectionRequest collectionDescription = buildDatasetCollectionListPairedRequest(collectionName,false);
    DatasetCollectionResponse response = 
        historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    
    Assert.assertEquals(collectionName, response.getName());
    Assert.assertEquals("list:paired", response.getCollectionType());
    Assert.assertNotNull(response.getId());
    
    List<CollectionResponse> elementsResponse = response.getElements();
    Assert.assertEquals(2, elementsResponse.size());
    
    CollectionResponse elementResponse0 = elementsResponse.get(0);
    Assert.assertEquals(0, elementResponse0.getElementIndex());
    Assert.assertNotNull(elementResponse0.getId());
    Assert.assertEquals("paired1", elementResponse0.getElementIdentifier());
    
    assertPairedResponseObject(elementResponse0.getResponseObject());

    CollectionResponse elementResponse1 = elementsResponse.get(1);
    Assert.assertEquals(1, elementResponse1.getElementIndex());
    Assert.assertNotNull(elementResponse1.getId());
    Assert.assertEquals("paired2", elementResponse1.getElementIdentifier());
    
    assertPairedResponseObject(elementResponse1.getResponseObject());
  }
  
  /**
   * Tests out building a new list of paired dataset collections and failing.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListPairedFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionListPairedPass";
    
    DatasetCollectionRequest collectionDescription = buildDatasetCollectionListPairedRequest(collectionName,true);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), 500, response.getStatus());
  }
  
  private void assertPairedResponseObject(ResponseObject responseObject) {
    Assert.assertTrue(responseObject instanceof DatasetCollectionResponse);
    DatasetCollectionResponse pairedResponse = (DatasetCollectionResponse)responseObject;
    
    Assert.assertEquals("paired", pairedResponse.getCollectionType());
    Assert.assertNotNull(pairedResponse.getId());
    
    List<CollectionResponse> elementsResponsePaired = pairedResponse.getElements();
    Assert.assertEquals(2, elementsResponsePaired.size());
    
    CollectionResponse elementResponsePairedElement0 = elementsResponsePaired.get(0);
    Assert.assertEquals(0, elementResponsePairedElement0.getElementIndex());
    Assert.assertNotNull(elementResponsePairedElement0.getId());
    Assert.assertEquals("forward", elementResponsePairedElement0.getElementIdentifier());
    
    CollectionResponse elementResponsePairedElement1 = elementsResponsePaired.get(1);
    Assert.assertEquals(1, elementResponsePairedElement1.getElementIndex());
    Assert.assertNotNull(elementResponsePairedElement1.getId());
    Assert.assertEquals("reverse", elementResponsePairedElement1.getElementIdentifier());
  }
  
  /**
   * Tests out building a new paired dataset collection and failing.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionPairedFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionPairedFail";
	  
    HistoryDatasetElementRequest element1 = new HistoryDatasetElementRequest();
    element1.setId(collectionDataset1.getId());
    element1.setName("forward");
    
    HistoryDatasetElementRequest element2 = new HistoryDatasetElementRequest();
    element2.setId(collectionDataset2.getId() + "makeFailNow");
    element2.setName("reverse");
    
    DatasetCollectionRequest collectionDescription = new DatasetCollectionRequest();
    collectionDescription.setCollectionType("paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    ClientResponse response = historiesClient.createDatasetCollectionRequest(collectionHistoryId, collectionDescription);
    Assert.assertEquals(responseErrorMessage(response), 500, response.getStatus());
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
