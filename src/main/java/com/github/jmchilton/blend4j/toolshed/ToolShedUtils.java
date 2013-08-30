package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.galaxy.beans.InstallableRepositoryRevision;
import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import com.github.jmchilton.blend4j.toolshed.beans.RepositoryRevision;

public class ToolShedUtils {

  public static InstallableRepositoryRevision getLatestInstallableRevision(final Repository repository) {
    return getLatestInstallableRevision(ToolShedInstanceFactory.getMainToolShedInstance(), repository);
  }
  
  public static InstallableRepositoryRevision getLatestInstallableRevision(final ToolShedInstance instance, final Repository repository) {
    final RepositoryRevision revision; 
    revision = RepositoryUtils.getLatestRepositoryRevision(instance.getRepositoriesClient(), repository);
    final InstallableRepositoryRevision installableRevision;
    installableRevision = new InstallableRepositoryRevision(instance.getUrl(), revision);    
    return installableRevision;
  }
  
}
