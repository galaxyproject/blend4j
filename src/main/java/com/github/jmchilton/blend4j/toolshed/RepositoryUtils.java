package com.github.jmchilton.blend4j.toolshed;

import com.github.jmchilton.blend4j.toolshed.beans.Repository;
import com.github.jmchilton.blend4j.toolshed.beans.RepositoryRevision;
import java.util.List;

public class RepositoryUtils {

  public static RepositoryRevision getLatestRepositoryRevision(final RepositoriesClient client, final String owner, final String name) {
    return getLatestRepositoryRevision(client, new Repository(owner, name));
  }
  
  public static RepositoryRevision getLatestRepositoryRevision(final RepositoriesClient client, final Repository repository) {
    final List<String> installableRevisions = client.getInstallableRevisions(repository);
    final String latest = installableRevisions.get(installableRevisions.size() - 1);
    return new RepositoryRevision(repository, latest);
  }
  
}
