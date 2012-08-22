package com.github.jmchilton.blend4j.galaxy;

import java.util.List;

import com.github.jmchilton.blend4j.galaxy.beans.Role;

public interface RolesClient {
  List<Role> getRoles();
  
  Role getRole(final String name);
  
}
