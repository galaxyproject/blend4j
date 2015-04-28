package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.github.jmchilton.blend4j.galaxy.beans.Job;
import com.github.jmchilton.blend4j.galaxy.beans.JobDetails;

/**
 * Implementation for interacting with Galaxy's Job API.
 * 
 * @author Franklin Bristow franklin.bristow@phac-aspc.gc.ca
 *
 */
public class JobsClientImpl extends Client implements JobsClient {

	JobsClientImpl(GalaxyInstanceImpl galaxyInstance) {
		super(galaxyInstance, "jobs");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Job> getJobs() {
		return get(new TypeReference<List<Job>>() {
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobDetails showJob(final String id) {
		return super.show(id, JobDetails.class);
	}

}
