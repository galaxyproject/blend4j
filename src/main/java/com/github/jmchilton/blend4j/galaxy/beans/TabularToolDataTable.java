package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabularToolDataTable extends GalaxyObject {
    private String name;

    private List<String> columns;

    private List<List<String>> fields;

    @JsonProperty("name")
    protected void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonProperty("columns")
    protected void setColumns(final List<String> columns) {
        this.columns = columns;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    @JsonProperty("fields")
    protected void setFields(final List<List<String>> fields) {
        this.fields = fields;
    }

    public List<List<String>> getFields() {
        return this.fields;
    }

    public List<String> getValues() {
        List<String> columns = getColumns();
        int valueIndex = columns.indexOf("value");
        List<List<String>> rows = getFields();
        List<String> values = new ArrayList<String>();
        for (List<String> row : rows) {
            values.add(row.get(valueIndex));
        }
        return values;
    }

    public List<String> getFieldsListForValue(String value) {
        List<String> columns = getColumns();
        int valueIndex = columns.indexOf("value");
        List<List<String>> rows = getFields();
        List<String> fields = new ArrayList<String>();
        for (List<String> row : rows) {
            if (row.get(valueIndex).equals(value)) {
                fields = row;
            }
        }
        return fields;
    }

    public Map<String, String> getFieldsMapForValue(String value) {
        List<String> columns = getColumns();
        int valueIndex = columns.indexOf("value");
        List<List<String>> rows = getFields();
        Map<String, String> fields = new HashMap<String, String>();
        for (List<String> row : rows) {
            if (row.get(valueIndex).equals(value)) {
                for (int i = 0; i < columns.size(); i++) {
                    fields.put(columns.get(i), row.get(i));
                }
            }
        }
        return fields;
    }

    public List<String> getFieldsForColumn(String column) {
        List<String> columns = getColumns();
        int columnIndex = columns.indexOf(column);
        List<List<String>> rows = getFields();
        List<String> fields = new ArrayList<String>();
        for (List<String> row : rows) {
            fields.add(row.get(columnIndex));
        }
        return fields;
    }

    public String getField(String value, String column) {
        String field = getFieldsMapForValue(value).get(column);
        return field;
    }
}
