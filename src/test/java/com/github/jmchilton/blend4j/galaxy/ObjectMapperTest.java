package com.github.jmchilton.blend4j.galaxy;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.LibraryPermissions;

// Not a true unit test, just a place to play around with Jackson mapping.
public class ObjectMapperTest {
  @Test
  public void testSerialization() throws JsonGenerationException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    LibraryPermissions libraryPermissions = new LibraryPermissions();
    libraryPermissions.getAccessInRoles().add("moocow");
    mapper.writer().writeValueAsString(libraryPermissions);

  }
}
