package com.github.jmchilton.blend4j.galaxy.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class JobDetails extends Job {
	@JsonProperty("command_line")
	private String commandLine;
	
	@JsonProperty("exit_code")
	private Integer exitCode;
	
	public Integer getExitCode() {
		return exitCode;
	}

	public void setExitCode(final Integer exitCode) {
		this.exitCode = exitCode;
	}

	public String getCommandLine() {
		return this.commandLine;
	}
	
	public void setCommandLine(final String commandLine) {
		this.commandLine = commandLine;
	}

}
