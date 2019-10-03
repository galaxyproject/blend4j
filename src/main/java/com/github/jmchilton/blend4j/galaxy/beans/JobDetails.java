package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;


/**
 * AMPPD extension
 * Bean for fields included in an job (an execution of a tool, which could correspond to a step in a workflow invocation), with inputs/outputs details.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class JobDetails extends Job {
	
	@JsonProperty("command_line")
	private String commandLine;
	
//	@JsonProperty("exit_code")
//	private Integer exitCode;

    private Map<String, JobInputOutput> inputs;

    private Map<String, JobInputOutput> outputs;
	
//	public Integer getExitCode() {
//		return exitCode;
//	}
//
//	public void setExitCode(final Integer exitCode) {
//		this.exitCode = exitCode;
//	}

	public String getCommandLine() {
		return this.commandLine;
	}
	
	public void setCommandLine(final String commandLine) {
		this.commandLine = commandLine;
	}

    public Map<String, JobInputOutput> getInputs() {
        return inputs;
    }

    public void setInputs(Map<String, JobInputOutput> inputs) {
        this.inputs = inputs;
    }

    public Map<String, JobInputOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(Map<String, JobInputOutput> outputs) {
        this.outputs = outputs;
    }
}
