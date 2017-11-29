package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import com.sun.jersey.api.client.ClientResponse;

import java.util.List;

public interface ToolDataClient {
    ClientResponse showDataTableRequest(String dataTableId);

    /**
     * Delete an item from a data table.
     * @param dataTableId ID of the data table
     * @param values list of column contents, there must be a value for all the columns of the data table
     * @return A {@link ClientResponse} for the deleted tool data table items. The status code
     *         provided by {@link ClientResponse#getClientResponseStatus()} should
     *         be verified for success.
     */
    ClientResponse deleteDataTableRequest(final String dataTableId, final List<String> values);

    /**
     * Get a list of all Tool Data Tables stored in Galaxy.
     *
     * @return the list of Tool Data Tables installed in Galaxy.
     */
    List<TabularToolDataTable> getDataTables();

    /**
     * Show details about the specified tool.
     *
     * @param dataTableId the Tool Data Table to look up.
     * @return updated details about the Tool Data Table.
     */
    // TabularToolDataTable reloadDataTable(final String dataTableId);

    /**
     * Show details about the specified tool.
     *
     * @param dataTableId the Tool Data Table to look up.
     * @return A {@link ClientResponse} for the reloaded tool data table. The status code
     *         provided by {@link ClientResponse#getClientResponseStatus()} should
     *         be verified for success.
     */
    ClientResponse reloadDataTableRequest(final String dataTableId);

    /**
     * Show details about the specified tool.
     *
     * @param dataTableId the Tool Data Table to look up.
     * @return details about the Tool Data Table.
     */
    TabularToolDataTable showDataTable(final String dataTableId);
}
