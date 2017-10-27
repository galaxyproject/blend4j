package com.github.jmchilton.blend4j.galaxy;

import java.util.List;
import java.util.Map;

public interface ToolDataClient {

    public List getDataTables();

    public Map showDataTable(String dataTableId);

    public Map reloadDataTable(String dataTableId);
}
