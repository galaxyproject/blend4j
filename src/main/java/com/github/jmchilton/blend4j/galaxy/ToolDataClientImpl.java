package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;

public class ToolDataClientImpl extends Client implements ToolDataClient {
    ToolDataClientImpl(GalaxyInstanceImpl galaxyInstance) {
        super(galaxyInstance, "tool_data");
    }

    public ClientResponse showDataTableRequest(String dataTableId) {
        return super.show(dataTableId, ClientResponse.class);
    }

    public ClientResponse deleteDataTableRequest(final String dataTableId, final List<String> values) {
        return deleteResponse(getWebResource(dataTableId));
    }

    public List<TabularToolDataTable> getDataTables() {
        return get(new TypeReference<List<TabularToolDataTable>>() {});
    }

    public ClientResponse reloadDataTableRequest(final String dataTableId) {
        return getResponse(getWebResource(dataTableId).path("reload"));
    }

    public TabularToolDataTable showDataTable(final String dataTableId) {
        return super.show(dataTableId, TabularToolDataTable.class);
    }
}
