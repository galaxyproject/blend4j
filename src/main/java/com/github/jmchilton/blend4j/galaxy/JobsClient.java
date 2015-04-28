package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Job;
import com.github.jmchilton.blend4j.galaxy.beans.JobDetails;

/**
 * Client for interacting with Galaxy's Job API.
 * 
 * @author Franklin Bristow franklin.bristow@phac-aspc.gc.ca
 *
 */
public interface JobsClient {

	/**
	 * Get *all* jobs executed by Galaxy. **WARNING**: This may take a long time
	 * to execute, depending on how many jobs Galaxy has executed in its
	 * history.
	 * 
	 * @return a collection of all jobs executed by Galaxy.
	 */
	public List<Job> getJobs();
	
	/**
	 * Show the details of a specific job execution.
	 * 
	 * @param id the job id
	 * @return the details of a job.
	 */
	public JobDetails showJob(final String id);
}
