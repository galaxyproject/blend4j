package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ToolDataTest {
    private GalaxyInstance instance;
    private ToolDataClient client;

    @BeforeMethod
    public void init() {
        instance = TestGalaxyInstance.get();
        client = instance.getToolDataClient();
    }

    @Test
    public void testGetDataTables() {
        final List<TabularToolDataTable> toolDataTables = client.getDataTables();
        assert toolDataTables != null;
        assert ! toolDataTables.isEmpty();

        for (final TabularToolDataTable toolDataTable : toolDataTables) {
            assert toolDataTable.getName() != null;
            System.out.println(toolDataTable.getName());
        }
    }
}
