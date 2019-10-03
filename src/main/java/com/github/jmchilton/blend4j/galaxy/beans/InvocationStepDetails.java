package com.github.jmchilton.blend4j.galaxy.beans;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * AMPPD extension
 * Bean for fields included in an invocation step, with job/outputs details.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvocationStepDetails extends InvocationStep {

	private List<Job> jobs;	
    private Map<String, JobInputOutput> outputs;
	// we omit the output_collection, since we don't use collection
    
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	public Map<String, JobInputOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(Map<String, JobInputOutput> outputs) {
		this.outputs = outputs;
	}
    
}
