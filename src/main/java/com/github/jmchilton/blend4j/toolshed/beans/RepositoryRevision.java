package com.github.jmchilton.blend4j.toolshed.beans;

// TODO: Swap this to use delegation instead of inheritence.
public class RepositoryRevision extends Repository {
  private String revision;

  public RepositoryRevision() {
  }
  
  public RepositoryRevision(final Repository repository, final String revision) {
    this(repository.getOwner(), repository.getName(), revision);
  }

  public RepositoryRevision(final String owner, final String name, final String revision) {
    super(owner, name);
    this.revision = revision;
  }

  public String getRevision() {
    return revision;
  }

  public void setRevision(final String revision) {
    this.revision = revision;
  }

}
