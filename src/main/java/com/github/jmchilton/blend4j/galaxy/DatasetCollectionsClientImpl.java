package com.github.jmchilton.blend4j.galaxy;

import com.sun.jersey.api.client.ClientResponse;

public class DatasetCollectionsClientImpl extends Client implements DatasetCollectionsClient {

  DatasetCollectionsClientImpl(GalaxyInstanceImpl galaxyInstance) {
    super(galaxyInstance, "dataset_collections");
  }

  @Override
  public ClientResponse createRequest(final DatasetCollectionInstanceCreate create) {
    return super.create(create);
  }
  
  @Override
  public DatasetCollectionInstanceSummary create(final DatasetCollectionInstanceCreate create) {
    return this.createRequest(create).getEntity(DatasetCollectionInstanceSummary.class);
  }
  
}
