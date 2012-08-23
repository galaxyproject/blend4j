package com.github.jmchilton.blend4j.galaxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.Test;

import com.github.jmchilton.blend4j.galaxy.beans.History;

public class Examples {
  
  public static void main(final String[] args) throws Exception {
    runExamples();
  }
    
  @Test
  public static void runExamples() throws Exception {
    final String[] exampleMethods = new String[] {"listHistories"};
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
