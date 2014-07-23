package com.github.jmchilton.blend4j.galaxy.beans.collection.request;

import com.github.jmchilton.blend4j.galaxy.beans.collection.CollectionSource;

/**
 * 
 * Class describing a history dataset to be placed within a collection.
 *
 */
public class HistoryDatasetElementRequest extends SimpleElementRequest {
  public HistoryDatasetElementRequest() {
    setSource(CollectionSource.HDA);
  }
}
