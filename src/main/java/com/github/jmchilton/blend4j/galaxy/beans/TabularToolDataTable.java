package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabularToolDataTable extends GalaxyObject {
    private String name;

    private ArrayList<String> columns;

    private ArrayList<ArrayList<String>> fields;

    @JsonProperty("name")
    protected void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonProperty("columns")
    protected void setColumns(final ArrayList<String> columns) {
        this.columns = columns;
    }

    public ArrayList<String> getColumns() {
        return this.columns;
    }

    @JsonProperty("fields")
    protected void setFields(final ArrayList<ArrayList<String>> fields) {
        this.fields = fields;
    }

    public ArrayList<ArrayList<String>> getFields() {
        return this.fields;
    }

}
