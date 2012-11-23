# About

blend4j is a partial reimplemenation of the Python library [blend][1]
for the JVM. blend for Python is a library for scripting interactions
with Galaxy, CloudMan, and BioCloudCentral. 

blend4j will focus initially on reimplementation the particulars parts
of blend I am in immediate need of (a subset of the Galaxy API
functionality), but hopefully over time it will grow into feature
parity with blend.

[1]: https://github.com/afgane/blend

# Usage

For simplicity, these examples assume the following import statements are used to import all of blend4j's Galaxy functionality.

    import com.github.jmchilton.blend4j.galaxy.beans.*;
    import com.github.jmchilton.blend4j.galaxy.*;

Listing current users history:

```java
    GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    HistoriesClient historiesClient = galaxyInstance.getHistoriesClient();
    for(History history : historiesClient.getHistories()) {
      String name = history.getName();
      String id = history.getId();
      String message = String.format("Found history with name %s and id %s", name, id);
      System.out.println(message);
    }
```

Find a data library by name and print its contents:

```java
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
```

The following code demonstrates running a workflow. It assumes the existence of
a workflow named TestWorkflow1 with two inputs labeled WorkflowInput1 and 
WorkflowInput2. Additionally it assumes a history named TestHistory1 already
exists containing two datasets Input1 and Input2.

```java
    final GalaxyInstance instance = GalaxyInstanceFactory.get(url, apiKey);
    final WorkflowsClient workflowsClient = instance.getWorkflowsClient();

    // Find history
    final HistoriesClient historyClient = instance.getHistoriesClient();
    History matchingHistory = null;
    for(final History history : historyClient.getHistories()) {
      if(history.getName().equals("TestHistory1")) {
        matchingHistory = history;
      }
    }
    Assert.assertNotNull(matchingHistory);
    String input1Id = null;
    String input2Id = null;
    for(final HistoryContents historyDataset :historyClient.showHistoryContents(matchingHistory.getId())) {
      if(historyDataset.getName().equals("Input1")) {
        input1Id = historyDataset.getId();
      }
      if(historyDataset.getName().equals("Input2")) {
        input2Id = historyDataset.getId();
      }
    }
    
    Workflow matchingWorkflow = null;
    for(Workflow workflow : workflowsClient.getWorkflows()) {
      if(workflow.getName().equals("TestWorkflow1")) {
        matchingWorkflow = workflow;
      }
    }

    final WorkflowDetails workflowDetails = workflowsClient.showWorkflow(matchingWorkflow.getId());
    String workflowInput1Id = null;
    String workflowInput2Id = null;
    for(final Map.Entry<String, WorkflowInputDefinition> inputEntry : workflowDetails.getInputs().entrySet()) {
      final String label = inputEntry.getValue().getLabel();
      if(label.equals("WorkflowInput1")) {
        workflowInput1Id = inputEntry.getKey();
      }
      if(label.equals("WorkflowInput2")) {
        workflowInput2Id = inputEntry.getKey();
      }
    }

    final WorkflowInputs inputs = new WorkflowInputs();
    inputs.setDestination(new WorkflowInputs.ExistingHistory(matchingHistory.getId()));
    inputs.setWorkflowId(matchingWorkflow.getId());
    inputs.setInput(workflowInput1Id, new WorkflowInputs.WorkflowInput(input1Id, WorkflowInputs.InputSourceType.HDA));
    inputs.setInput(workflowInput2Id, new WorkflowInputs.WorkflowInput(input2Id, WorkflowInputs.InputSourceType.HDA));
    final WorkflowOutputs output = workflowsClient.runWorkflow(inputs);    
    System.out.println("Running workflow in history " + output.getHistoryId());
    for(String outputId : output.getOutputIds()) {
      System.out.println("  Workflow writing to output id " + outputId);
    }
```

The following code demonstrates creating a data library at four different levels of abstraction (these examples require an admin key):

```java
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
```

This example demonstrates an admin process creating a private data library for a user and copying a directories contents to it. This example requires both an admin key and for use_remote_user to be enabled in the Galaxy instance's options file.

```java
    final GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(url, apiKey);
    
    final String email = "alice@example.com";
    
    // Create data library
    final Library library = new Library("Example library for " + email);
    final LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
    final Library persistedLibrary = librariesClient.createLibrary(library);
    
    // Copy example directory into library
    final FilesystemPathsLibraryUpload upload = new FilesystemPathsLibraryUpload();
    final LibraryContent rootFolder = librariesClient.getRootFolder(persistedLibrary.getId());
    upload.setFolderId(rootFolder.getId());
    upload.setContent("test-data/variant_detection");
    librariesClient.uploadFilesystemPathsRequest(persistedLibrary.getId(), upload);
    
    // Obtain user object
    User owner = null;
    final UsersClient usersClient = galaxyInstance.getUsersClient();
    for(final User user : usersClient.getUsers()) {
      if(user.getEmail().equals(email)) {
        owner = user;
        break;
      }
    }
    if(owner == null) {
      // In order to create users like this - use_remote_user must be enabled
      // in the Galaxy instance's universe_wsgi.ini options.
      owner = usersClient.createUser(email);
    }
    
    // Obtain user role
    Role ownersPrivateRole = null;
    final RolesClient rolesClient = galaxyInstance.getRolesClient();
    for(final Role role : rolesClient.getRoles()) {
      if(role.getName().equals(email)) {
        ownersPrivateRole = role;
        break;
      }
    }
    final String ownersPrivateRoleId = ownersPrivateRole.getId(); 
    
    // Set data library permissions
    final LibraryPermissions permissions = new LibraryPermissions();
    permissions.getAccessInRoles().add(ownersPrivateRoleId);
    permissions.getAddInRoles().add(ownersPrivateRoleId);
    permissions.getManageInRoles().add(ownersPrivateRoleId);
    permissions.getModifyInRoles().add(ownersPrivateRoleId);
    librariesClient.setLibraryPermissions(persistedLibrary.getId(), permissions);
```

These examples are taken from [Examples.java][u0]. For more examples see these [integration test cases][u1].

Brad Chapman has put together a higher-level Clojure library - [clj-blend][u2], the
source code of which can serve as additional examples of blend4j
usage.


[u0]: https://github.com/jmchilton/blend4j/blob/master/src/test/java/com/github/jmchilton/blend4j/galaxy/Examples.java
[u1]: https://github.com/jmchilton/blend4j/blob/master/src/test/java/com/github/jmchilton/blend4j/galaxy/IntegrationTest.java
[u2]: https://github.com/chapmanb/clj-blend.git

# Building

blend4j can be built with [Apache Maven][b1].

        % git clone git://github.com/jmchilton/blend4j.git
        % cd blend4j
        % mvn compile

[b1]: http://maven.apache.org/

# Downloading Jars

The latest blend4j jar can be downloaded from [MSI's Artifactory repository][d0].

[d0]: http://artifactory.msi.umn.edu/simple/libs-snapshot-local/com/github/jmchilton/blend4j/blend4j/0.1-SNAPSHOT/

# License

The code is freely available under the [Apache License Version 2.0][l1].

[l1]: http://www.apache.org/licenses/LICENSE-2.0.html

