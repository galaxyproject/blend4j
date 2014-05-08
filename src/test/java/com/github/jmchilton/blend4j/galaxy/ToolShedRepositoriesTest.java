package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository;
import com.github.jmchilton.blend4j.galaxy.beans.InstalledRepository.InstallationStatus;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryInstall;
import com.github.jmchilton.blend4j.galaxy.beans.RepositoryWorkflow;
import com.github.jmchilton.blend4j.galaxy.beans.Workflow;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToolShedRepositoriesTest {
  private GalaxyInstance instance;
  private ToolShedRepositoriesClient reposClient;

  @BeforeMethod
  public void init() {
    instance = TestGalaxyInstance.get();
    reposClient = instance.getRepositoriesClient();
  }

  @Test
  public void testInstall() {
    RepositoryInstall install = new RepositoryInstall();
    install.setChangsetRevision("a38cd98a6b41");
    install.setName("dbbuilder");
    install.setOwner("galaxyp");
    final List<InstalledRepository> repositories = reposClient.installRepository(install);
    assert repositories.size() > 0;
  }
  
  @Test
  public void testRepositoryWorkflows() throws InterruptedException {
    RepositoryInstall install = new RepositoryInstall();
    install.setInstallRepositoryDependencies(false);
    install.setInstallToolDependencies(false);
    install.setChangsetRevision("c8261328a9ff");
    install.setName("mmuflr");
    install.setOwner("jjohnson");
    final InstalledRepository repository = reposClient.installRepository(install).get(0);
    final String repositoryId = repository.getId();
    while(!reposClient.showRepository(repositoryId).getInstallationStatus().isComplete()) {
      System.out.println("Waiting...");
      Thread.sleep(100l);
    }
    assert reposClient.showRepository(repositoryId).getInstallationStatus().equals(InstallationStatus.INSTALLED);
    final List<RepositoryWorkflow> workflows = reposClient.exportedWorkflows(repositoryId);
    assert workflows.size() == 3 : "Incorrect number of exported workflows discovered - " + workflows.size();
    final Workflow workflow = reposClient.importWorkflow(repositoryId, workflows.get(0).getIndex());
    assert workflow != null : "Returned workflow is null.";
  }

}
