package com.github.jmchilton.blend4j.galaxy;

import static org.testng.AssertJUnit.*;

import com.github.jmchilton.blend4j.exceptions.ResponseException;
import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDeleteResponse;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryExport;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.AbstractElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionDescription;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.CollectionElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.request.HistoryDatasetElement;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionElementResponse;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.CollectionResponse;
import com.github.jmchilton.blend4j.galaxy.beans.collection.response.ElementResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HistoriesTest {
  private GalaxyInstance instance;
  private ToolsClient toolsClient;
  private HistoriesClient historiesClient;
  
  private File downloadFile;
  private OutputDataset downloadOutputDataset;
  private String downloadHistoryId;
  private String downloadDatasetId;
  
  private File collectionFile1;
  private File collectionFile2;
  private OutputDataset collectionDataset1;
  private OutputDataset collectionDataset2;
  
  private String collectionHistoryId;
  
  private static final String invalidDatasetId = "invalid";
  private static final String invalidHistoryId = "invalid";

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
    
    // Galaxy adds an extra newline to a file on upload
    // add newline here so tests pass when comparing files.
    downloadFile = TestHelpers.getTestFile("hello_with_newline\n");
    downloadOutputDataset = TestHelpers.testUpload(instance, collectionHistoryId, downloadFile);
    downloadHistoryId = collectionHistoryId;
    downloadDatasetId = downloadOutputDataset.getId();
    
    TestHelpers.waitForHistory(historiesClient, collectionHistoryId);
  }
  
  /**
   * Checks to make sure the given dataset collection response matches the collection description.
   * @param collectionResponse  The DatasetCollection response from the server.
   * @param collectionDescription  The description of the dataset collection to send to the server.
   */
  private void assertDatasetCollectionResponseValid(CollectionResponse collectionResponse, 
    CollectionDescription collectionDescription) {
    
    Assert.assertEquals(collectionDescription.getName(), collectionResponse.getName());
    Assert.assertEquals(collectionDescription.getCollectionType(), collectionResponse.getCollectionType());
    Assert.assertNotNull(collectionResponse.getId());
    
    List<CollectionElementResponse> elementsResponse = collectionResponse.getElements();
    List<AbstractElement> elementsDescription = collectionDescription.getDatasetElements();
    
    Assert.assertEquals(elementsDescription.size(), elementsResponse.size());
    
    for (int i = 0; i < elementsDescription.size(); i++) {
      AbstractElement elementDescription = elementsDescription.get(i);
      CollectionElementResponse elementResponse = elementsResponse.get(i);
      
      Assert.assertEquals(i, elementResponse.getElementIndex());
      Assert.assertNotNull(elementResponse.getId());
      
      Assert.assertEquals(elementDescription.getName(), elementResponse.getElementIdentifier());
    }
  }

  @Test
  public void testShowHistory() {
    //final HistoriesClient client = IntegrationTest.getHistoriesClient();
    // TODO:
  }
  
  private CollectionDescription buildDatasetCollectionListRequest(String collectionName, boolean fail) {
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId() + (fail ? "fail" : ""));
    element1.setName(collectionDataset1.getName());
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId());
    element2.setName(collectionDataset2.getName());
    
    CollectionDescription collectionDescription = new CollectionDescription();
    collectionDescription.setCollectionType("list");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    return collectionDescription;
  }
  
  /**
   * Tests out building a new list dataset collection successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionListPass() throws InterruptedException {
	  String collectionName = "testCreateDatasetCollectionListPass";
	  
    CollectionDescription collectionDescription = buildDatasetCollectionListRequest(collectionName, false);
    
    CollectionResponse collection = historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    assertDatasetCollectionResponseValid(collection, collectionDescription);
  }
  
  /**
   * Tests out building a new list dataset collection and failing.
   * @throws InterruptedException
   */
  @Test(expectedExceptions = RuntimeException.class)
  public void testCreateDatasetCollectionListFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionListFail";
    
    CollectionDescription collectionDescription = buildDatasetCollectionListRequest(collectionName, true);
    
    historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
  }
  
  /**
   * Tests out the showDatasetCollection method with a list of data.
   */
  @Test
  public void testShowDatasetCollectionsListPass() {
    String collectionName = "testShowDatasetCollectionsListPass";
    
    CollectionDescription collectionDescription = buildDatasetCollectionListRequest(collectionName,false);
    CollectionResponse responseCreation = 
        historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    
    CollectionResponse responseShow =
        historiesClient.showDatasetCollection(collectionHistoryId, responseCreation.getId());
    
    Assert.assertEquals(responseCreation, responseShow);
  }
  
  private CollectionDescription buildDatasetCollectionPairedRequest(String collectionName, boolean fail) {
    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId() + (fail ? "fail" : ""));
    element1.setName("forward");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId());
    element2.setName("reverse");
    
    CollectionDescription collectionDescription = new CollectionDescription();
    collectionDescription.setCollectionType("paired");
    collectionDescription.setName(collectionName);
    collectionDescription.addDatasetElement(element1);
    collectionDescription.addDatasetElement(element2);
    
    return collectionDescription;
  }
  
  /**
   * Tests out building a new paired dataset collection successfully.
   * @throws InterruptedException
   */
  @Test
  public void testCreateDatasetCollectionPairedPass() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionPairedPass";
	  
    CollectionDescription collectionDescription = buildDatasetCollectionPairedRequest(collectionName, false);
    
    CollectionResponse collection = historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    assertDatasetCollectionResponseValid(collection, collectionDescription);
  }
  
  
  /**
   * Tests out building a new paired dataset collection and failing.
   * @throws InterruptedException
   */
  @Test(expectedExceptions = RuntimeException.class)
  public void testCreateDatasetCollectionPairedFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionPairedFail";
    
    CollectionDescription collectionDescription = buildDatasetCollectionPairedRequest(collectionName, true);
    
    historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
  }
  
  /**
   * Tests out the showDatasetCollection method with paired data.
   */
  @Test
  public void testShowDatasetCollectionsPairedPass() {
    String collectionName = "testShowDatasetCollectionsPairedPass";
    
    CollectionDescription collectionDescription = buildDatasetCollectionPairedRequest(collectionName,false);
    CollectionResponse responseCreation = 
        historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    
    CollectionResponse responseShow =
        historiesClient.showDatasetCollection(collectionHistoryId, responseCreation.getId());
    
    Assert.assertEquals(responseCreation, responseShow);
  }
  
  private void assertPairedResponseObject(ElementResponse responseObject) {
    Assert.assertTrue(responseObject instanceof CollectionResponse);
    CollectionResponse pairedResponse = (CollectionResponse)responseObject;
    
    Assert.assertEquals("paired", pairedResponse.getCollectionType());
    Assert.assertNotNull(pairedResponse.getId());
    
    List<CollectionElementResponse> elementsResponsePaired = pairedResponse.getElements();
    Assert.assertEquals(2, elementsResponsePaired.size());
    
    CollectionElementResponse elementResponsePairedElement0 = elementsResponsePaired.get(0);
    Assert.assertEquals(0, elementResponsePairedElement0.getElementIndex());
    Assert.assertNotNull(elementResponsePairedElement0.getId());
    Assert.assertEquals("forward", elementResponsePairedElement0.getElementIdentifier());
    
    CollectionElementResponse elementResponsePairedElement1 = elementsResponsePaired.get(1);
    Assert.assertEquals(1, elementResponsePairedElement1.getElementIndex());
    Assert.assertNotNull(elementResponsePairedElement1.getId());
    Assert.assertEquals("reverse", elementResponsePairedElement1.getElementIdentifier());
  }
  
  private CollectionDescription buildDatasetCollectionListPairedRequest(String collectionName, boolean fail) {

    HistoryDatasetElement element1 = new HistoryDatasetElement();
    element1.setId(collectionDataset1.getId() + (fail ? "fail" : ""));
    element1.setName("forward");
    
    HistoryDatasetElement element2 = new HistoryDatasetElement();
    element2.setId(collectionDataset2.getId());
    element2.setName("reverse");
    
    CollectionElement pairedSet1 = new CollectionElement();
    pairedSet1.setName("paired1");
    pairedSet1.setCollectionType("paired");
    pairedSet1.addCollectionElement(element1);
    pairedSet1.addCollectionElement(element2);
    
    CollectionElement pairedSet2 = new CollectionElement();
    pairedSet2.setName("paired2");
    pairedSet2.setCollectionType("paired");
    pairedSet2.addCollectionElement(element1);
    pairedSet2.addCollectionElement(element2);
    
    CollectionDescription collectionDescription = new CollectionDescription();
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
    
    CollectionDescription collectionDescription = buildDatasetCollectionListPairedRequest(collectionName,false);
    CollectionResponse response = 
        historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    
    Assert.assertEquals(collectionName, response.getName());
    Assert.assertEquals("list:paired", response.getCollectionType());
    Assert.assertNotNull(response.getId());
    
    List<CollectionElementResponse> elementsResponse = response.getElements();
    Assert.assertEquals(2, elementsResponse.size());
    
    CollectionElementResponse elementResponse0 = elementsResponse.get(0);
    Assert.assertEquals(0, elementResponse0.getElementIndex());
    Assert.assertNotNull(elementResponse0.getId());
    Assert.assertEquals("paired1", elementResponse0.getElementIdentifier());
    
    assertPairedResponseObject(elementResponse0.getResponseElement());

    CollectionElementResponse elementResponse1 = elementsResponse.get(1);
    Assert.assertEquals(1, elementResponse1.getElementIndex());
    Assert.assertNotNull(elementResponse1.getId());
    Assert.assertEquals("paired2", elementResponse1.getElementIdentifier());
    
    assertPairedResponseObject(elementResponse1.getResponseElement());
  }
  
  /**
   * Tests out building a new list of paired dataset collections and failing.
   * @throws InterruptedException
   */
  @Test(expectedExceptions = RuntimeException.class)
  public void testCreateDatasetCollectionListPairedFail() throws InterruptedException {
    String collectionName = "testCreateDatasetCollectionListPairedFail";
    
    CollectionDescription collectionDescription = buildDatasetCollectionListPairedRequest(collectionName,true);
    
    historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
  }
  
  /**
   * Tests out the showDatasetCollection method with a list of paired data.
   */
  @Test
  public void testShowDatasetCollectionsListPairedPass() {
    String collectionName = "testShowDatasetCollectionsListPairedPass";
    
    CollectionDescription collectionDescription = buildDatasetCollectionListPairedRequest(collectionName,false);
    CollectionResponse responseCreation = 
        historiesClient.createDatasetCollection(collectionHistoryId, collectionDescription);
    
    CollectionResponse responseShow =
        historiesClient.showDatasetCollection(collectionHistoryId, responseCreation.getId());
    
    Assert.assertEquals(responseCreation, responseShow);
  }
  
  /**
   * Tests attempting to show a dataset collection by passing a fake collection id.
   */
  @Test(expectedExceptions = RuntimeException.class)
  public void testShowDatasetCollectionFail() {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    
    historiesClient.showDatasetCollection(historyId, "fake");
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
  public void testProvenance() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final HistoryContents contents = getTestHistoryDataset(historyId);
    final HistoryContentsProvenance prov = historiesClient.showProvenance(historyId, contents.getId());
    // TODO: Test some stuff about prov - for now it is verifying serialization
    // etc... though.
  }

  @Test
  public void testShowDataset() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final HistoryContents contents = getTestHistoryDataset(historyId);
    final Dataset dataset = historiesClient.showDataset(historyId, contents.getId());
    assert dataset.getDataType().equals("txt") : dataset.getDataType();
    assert dataset.getDataTypeExt().equals("txt") : dataset.getDataTypeExt();
  }

  private HistoryContents getTestHistoryDataset(final String historyId) throws InterruptedException {
    final File testFile = TestHelpers.getTestFile();
    final ToolsClient.FileUploadRequest request = new ToolsClient.FileUploadRequest(historyId, testFile);
    final ClientResponse clientResponse = toolsClient.uploadRequest(request);
    
    final List<HistoryContents> contentsList = historiesClient.showHistoryContents(historyId);
    final HistoryContents contents = contentsList.get(0);
    TestHelpers.waitForHistory(historiesClient, historyId);
    return contents;
  }
  
  /**
   * Tests out downloading a dataset and succeeding.
   * 
   * @throws Exception
   */
  @Test
  public void testDownloadDatasetSuccess() throws Exception {
    File destinationFile = File.createTempFile("output", "dat");
    destinationFile.deleteOnExit();

    historiesClient.downloadDataset(downloadHistoryId, downloadDatasetId,
        destinationFile);
    Assert.assertEquals("files should have equal length",
        downloadFile.length(), destinationFile.length());
    Assert.assertTrue("files should have same content",
        TestHelpers.compareFileContents(downloadFile, destinationFile));
  }
  
  /**
   * Tests out downloading a dataset with an invalid history id.
   * @throws IOException 
   */
  @Test(expectedExceptions=RuntimeException.class)
  public void testDownloadDatasetFailDatasetId() throws IOException {    
    File destinationFile = File.createTempFile("output", "dat");
    destinationFile.deleteOnExit();
    
    historiesClient.downloadDataset(invalidHistoryId, downloadDatasetId, destinationFile);
  }
  
  /**
   * Tests out downloading a dataset with an invalid dataset id.
   * @throws IOException 
   */
  @Test(expectedExceptions=RuntimeException.class)
  public void testDownloadDatasetFailHistoryId() throws IOException {    
    File destinationFile = File.createTempFile("output", "dat");
    destinationFile.deleteOnExit();
    
    historiesClient.downloadDataset(downloadHistoryId, invalidDatasetId, destinationFile);
  }
  
  /**
   * Tests out successfully deleting a history and getting back a ClientRresponse.
   */
  @Test
  public void testDeleteHistoryResponseSuccess() {
    History createdHistory = historiesClient.create(new History("New History"));
    assert historiesClient.showHistory(createdHistory.getId()) != null : "History not properly created";
    
    ClientResponse deleteResponse = historiesClient.deleteHistoryResponse(createdHistory.getId(), false);
    assert ClientResponse.Status.OK.equals(deleteResponse.getClientResponseStatus()) : "Invalid status code for deleted history";
    
    try {
      historiesClient.showHistory(createdHistory.getId());
      fail("History not properly deleted");
    } catch (UniformInterfaceException e) {
      assert 400 == e.getResponse().getStatus() : "Invalid status code for deleted history";
    }
  }
  
  /**
   * Tests out failing to delete a non-existent history.
   */
  @Test
  public void testDeleteHistoryResponseFail() {
    String historyId = "invalid";
    
    assert !ClientResponse.Status.OK.equals(historiesClient.showHistoryRequest(historyId)) : "History with " + historyId + " already exists";
    
    try {
      historiesClient.deleteHistoryResponse(historyId, false);
      fail("Deleting invalid history did not throw an exception");
    } catch (ResponseException e) {
      assert 400 == e.getStatusCode() : "Invalid status code";      
    }
  }
  
  /**
   * Tests out successfully deleting a history.
   */
  @Test
  public void testDeleteHistorySuccess() {
    History createdHistory = historiesClient.create(new History("New History"));
    assert historiesClient.showHistory(createdHistory.getId()) != null : "History not properly created";
    
    HistoryDeleteResponse deleteResponse = historiesClient.deleteHistory(createdHistory.getId(), false);
    assert createdHistory.getId().equals(deleteResponse.getId()) : "Invalid id from delete response";
    assert deleteResponse.getDeleted() : "Invalid deleted status from response";
    assert !deleteResponse.getPurged() : "Invalid purged status from response";
    
    try {
      historiesClient.showHistory(createdHistory.getId());
      fail("History not properly deleted");
    } catch (UniformInterfaceException e) {
      assert 400 == e.getResponse().getStatus() : "Invalid status code for deleted history";
    }
  }
  
  /**
   * Tests out successfully purging a history.
   */
  @Test
  public void testPurgeHistorySuccess() {
    History createdHistory = historiesClient.create(new History("New History"));
    assert historiesClient.showHistory(createdHistory.getId()) != null : "History not properly created";
    
    HistoryDeleteResponse deleteResponse = historiesClient.deleteHistory(createdHistory.getId(), true);
    assert createdHistory.getId().equals(deleteResponse.getId()) : "Invalid id from delete response";
    assert deleteResponse.getDeleted() : "Invalid deleted status from response";
    assert deleteResponse.getPurged() : "Invalid purged status from response";
    
    try {
      historiesClient.showHistory(createdHistory.getId());
      fail("History not properly deleted");
    } catch (UniformInterfaceException e) {
      assert 400 == e.getResponse().getStatus() : "Invalid status code for deleted history";
    }
  }
  
  /**
   * Tests out failing to delete a non-existent history.
   */
  @Test
  public void testDeleteHistoryFail() {
    String historyId = "invalid";
    
    assert !ClientResponse.Status.OK.equals(historiesClient.showHistoryRequest(historyId)) : "History with " + historyId + " already exists";
    
    try {
      historiesClient.deleteHistory(historyId, false);
      fail("Deleting invalid history did not throw an exception");
    } catch (ResponseException e) {
      assert 400 == e.getStatusCode() : "Invalid status code";      
    }
  }
}
