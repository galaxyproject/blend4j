package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ToolDataClientImpl extends Client implements ToolDataClient {
    ToolDataClientImpl(GalaxyInstanceImpl galaxyInstance) {
        super(galaxyInstance, "tool_data");
    }

    public ClientResponse showDataTableRequest(String dataTableId) {
        return null;
    }

    public List<TabularToolDataTable> getDataTables() {
        return get(new TypeReference<List<TabularToolDataTable>>() {});
    }

    public TabularToolDataTable showDataTable(final String dataTableId) {
        return super.show(dataTableId, TabularToolDataTable.class);
    }
}
