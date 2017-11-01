package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import com.sun.jersey.api.client.ClientResponse;

import java.util.List;

public interface ToolDataClient {
    ClientResponse showDataTableRequest(String dataTableId);

    /**
     * Get a list of all Tool Data Tables stored in Galaxy.
     *
     * @return the list of Tool Data Tables installed in Galaxy.
     */
    public List<TabularToolDataTable> getDataTables();

    /**
     * Show details about the specified tool.
     *
     * @param dataTableId the Tool Data Table to look up.
     * @return details about the Tool Data Table.
     */
    public TabularToolDataTable showDataTable(final String dataTableId);
}
