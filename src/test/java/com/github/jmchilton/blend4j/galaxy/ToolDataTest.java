package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.TabularToolDataTable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
        }
    }

    @Test
    public void testShowDataTables() {
        final List<TabularToolDataTable> toolDataTables = client.getDataTables();
        assert toolDataTables != null;
        assert ! toolDataTables.isEmpty();

        for (final TabularToolDataTable toolDataTable : toolDataTables) {
            assert toolDataTable.getName() != null;
            TabularToolDataTable dataTable = client.showDataTable("igv_broad_genomes");

        }

    }

    @Test
    public void testTableContents() {
        final TabularToolDataTable igvBroadGenomes = client.showDataTable("igv_broad_genomes");
        assert igvBroadGenomes != null;

        final ArrayList<String> headers = igvBroadGenomes.getColumns();
        System.out.println(headers.get(0) + "\t" + headers.get(2));
        System.out.println("----------");
        for (final ArrayList<String> row : igvBroadGenomes.getFields()) {
            System.out.println(row.get(0) + "\t" + row.get(2));
        }

    }

}
