package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabularToolDataTable extends GalaxyObject {
    private String name;

    private List<String> columns;

    private List<String> fields;

    @JsonProperty("name")
    protected void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonProperty("columns")
    protected void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
