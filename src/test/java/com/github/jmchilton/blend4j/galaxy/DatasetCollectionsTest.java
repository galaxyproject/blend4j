package com.github.jmchilton.blend4j.galaxy;

import com.beust.jcommander.internal.Maps;
import com.github.jmchilton.blend4j.galaxy.DatasetCollectionsClient.DatasetCollectionInstanceCreate;
import com.github.jmchilton.blend4j.galaxy.DatasetCollectionsClient.DatasetCollectionInstanceSummary;
import com.github.jmchilton.blend4j.galaxy.DatasetCollectionsClient.DatasetIdentifier;
import com.github.jmchilton.blend4j.galaxy.DatasetCollectionsClient.HistoryDatasetCollectionInstanceCreate;
import com.github.jmchilton.blend4j.galaxy.HistoriesClient.IndexHistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DatasetCollectionsTest {
  private GalaxyInstance instance;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
  }

  @Test
  public void testHistoryDatasetCollection() throws InterruptedException {
    final String historyId = TestHelpers.getTestHistoryId(instance);
    final List<String> ids = TestHelpers.populateTestDatasets(instance, historyId, 2);

    final String input1Id = ids.get(0);
    final String input2Id = ids.get(1);
    
    final DatasetIdentifier identifier1 = new DatasetIdentifier(input1Id, DatasetCollectionsClient.Source.HDA);
    final DatasetIdentifier identifier2 = new DatasetIdentifier(input2Id, DatasetCollectionsClient.Source.HDA);

    final Map<String, DatasetIdentifier> datasetIdentifiers = Maps.newHashMap();
    datasetIdentifiers.put("left", identifier1);
    datasetIdentifiers.put("right", identifier2);
    
    final HistoryDatasetCollectionInstanceCreate create = new HistoryDatasetCollectionInstanceCreate();
    create.setDatasetIdentifiers(datasetIdentifiers);
    create.setCollectionType("paired");
    create.setName("MY COOL COLLECTION");
    create.setHistoryId(historyId);
    
    final ClientResponse request = instance.getDatasetCollectionsClient().createRequest(create);
    System.out.println(request.getEntity(String.class));
    
    final IndexHistoryContents index = new IndexHistoryContents();
    index.setHistoryId(historyId);
    System.out.println(instance.getHistoriesClient().showHistoryContentsRequest(index).getEntity(String.class));

    final List<String> allTypes = Lists.newArrayList("datasets", "collections");
    index.setTypes(allTypes);
    List<HistoryContents> contents = instance.getHistoriesClient().showHistoryContents(index);
    assert contents.size() == 3;

    final List<String> collecitonsOnly = Lists.newArrayList("collections");
    index.setTypes(collecitonsOnly);    
    contents = instance.getHistoriesClient().showHistoryContents(index);
    assert contents.size() == 1;
    
  
  }
  
}
