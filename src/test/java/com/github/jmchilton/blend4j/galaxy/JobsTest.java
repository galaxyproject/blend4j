package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.Job;
import com.github.jmchilton.blend4j.galaxy.beans.JobDetails;

/**
 * Tests for Galaxy's Jobs API.
 * 
 * @author Franklin Bristow franklin.bristow@phac-aspc.gc.ca
 *
 */
public class JobsTest {
	private GalaxyInstance instance;
	private JobsClient client;

	@BeforeMethod
	public void init() {
		instance = TestGalaxyInstance.get();
		client = instance.getJobsClient();
	}

	@Test
	public void testGetJobs() {
		final List<Job> jobs = client.getJobs();
		assert jobs != null;
		assert !jobs.isEmpty();

		for (final Job job : jobs) {
			assert job.getId() != null;
		}
	}
	
	@Test
	public void testGetJobDetails() {
		final List<Job> jobs = client.getJobs();
		assert jobs != null;
		assert !jobs.isEmpty();
		
		final Job job = jobs.iterator().next();
		final JobDetails deets = client.showJob(job.getId());
		
		assert deets != null;
		assert deets.getCommandLine() != null;
		assert deets.getExitCode() != null;
	}
}
