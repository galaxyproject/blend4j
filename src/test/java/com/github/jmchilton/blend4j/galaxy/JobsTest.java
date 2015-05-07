package com.github.jmchilton.blend4j.galaxy;

import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.History;
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
	private HistoriesClient hClient;

	@BeforeMethod
	public void init() {
		instance = TestGalaxyInstance.get();
		client = instance.getJobsClient();
		hClient = instance.getHistoriesClient();
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

	/**
	 * check that the jobs filtered by history id are a subset of the full set
	 * of jobs.
	 */
	@Test
	public void testGetJobsForHistory() {
		final List<Job> allJobs = client.getJobs();

		assert allJobs != null;
		assert !allJobs.isEmpty();

		final List<History> histories = hClient.getHistories();
		assert histories != null;
		assert !histories.isEmpty();

		final Iterator<History> historyIterator = histories.iterator();
		History history; 
		List<Job> historyJobs;
		do {
			history  = historyIterator.next();
			historyJobs = client.getJobsForHistory(history.getId());
		} while(historyJobs.size() == 0 && historyIterator.hasNext());
		
		assert historyJobs != null;
		assert !historyJobs.isEmpty();
		
		assert allJobs.size() >= historyJobs.size();
		assert allJobs.containsAll(historyJobs);
	}
}
