package com.github.jmchilton.blend4j.galaxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Examples {
  
  public static void main(final String[] args) throws Exception {
    runExamples();
  }
    
  @Test
  public static void runExamples() throws Exception {
    final String[] exampleMethods = new String[] {
        "listHistories",
        "listLibraryContents",
        "levelsOfAbstraction"
    };
    for(final String exampleMethod : exampleMethods) {
      runExample(exampleMethod);
    }  
  }
  
  public static void listHistories(final String url, final String apiKey) {
    GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    HistoriesClient historiesClient = galaxyInstance.getHistoriesClient();
    for(History history : historiesClient.getHistories()) {
      String name = history.getName();
      String id = history.getId();
      String message = String.format("Found history with name %s and id %s", name, id);
      System.out.println(message);
    }
  }
  
  public static void listLibraryContents(final String url, final String apiKey) {
    // Find a data library by name and print its contents 
    final GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    final LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
    final List<Library> libraries = librariesClient.getLibraries();
    Library testLibrary = null;
    for(final Library library : libraries) {
      if(library.getName().equals("test-library")) {
        testLibrary = library;
      }
    }
    if(testLibrary == null) {
      return;
    }
    for(final LibraryContent content : librariesClient.getLibraryContents(testLibrary.getId())) {
      final String type = content.getType(); // file or folder
      final String name = content.getName();
      final String id = content.getId();
      final String message = String.format("Found library content of type %s with name %s and id %s", type, name, id);
      System.out.println(message);
    }
  }

  public static void levelsOfAbstraction(final String url, final String apiKey) {
    // Most API methods have corresponding blend4j methods for dealing with 
    // both low-level request and parsed POJO responses. You can also use the method
    // galaxyInstance.getWebResource() to access the low-level Jersey APIs directly.
    final GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    final LibrariesClient librariesClient = galaxyInstance.getLibrariesClient(); 
    
    // Highest level of abstraction, deal with POJO responses
    final Library testLibrary1 = new Library("test1");
    final Library persistedLibrary1 = librariesClient.createLibrary(testLibrary1);
    
    // Deal with Jersey ClientResponse object in case want to check return status, etc...
    final Library testLibrary2 = new Library("test2");
    final ClientResponse response2 = librariesClient.createLibraryRequest(testLibrary2);
    if(response2.getStatus() == 200) {
      final Library persistedLibrary2 = response2.getEntity(Library.class);
      // ...
    }
    
    // Use Jersey directly (with POJOs)
    final WebResource webResource3 = galaxyInstance.getWebResource();
    final Library testLibrary3 = new Library("test3");
    final ClientResponse response3 = webResource3
        .path("libraries")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .post(ClientResponse.class, testLibrary3);
    final Library persistedLibrary3 = response3.getEntity(Library.class);
    
    // Use Jersey directly (no POJOs)
    final WebResource webResource4 = galaxyInstance.getWebResource();
    final ClientResponse response4 = webResource4
        .path("libraries")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .post(ClientResponse.class, "{\"name\": \"test4\"}");
    final String jsonResponse4 = response4.getEntity(String.class);
    System.out.println("JSON response is: " + jsonResponse4);
  }
  
  
  private static void runExample(final String methodName) throws Exception {
    final Method method = findExampleMethod(methodName);
    final String testInstanceUrl = TestGalaxyInstance.getTestInstanceUrl();
    final String testApiKey = TestGalaxyInstance.getTestApiKey();
    method.invoke(null, testInstanceUrl, testApiKey);    
  }
  
  private static Method findExampleMethod(final String methodName) {
    Method matchingMethod = null;
    for(final Method method : Examples.class.getMethods()) {
      if(method.getName().equals(methodName) && Modifier.isStatic(method.getModifiers())) { 
         matchingMethod = method;
      }
    }
    return matchingMethod;
  }
  
}
