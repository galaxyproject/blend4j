package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryDetails;
import com.github.jmchilton.blend4j.galaxy.beans.OutputDataset;
import com.github.jmchilton.blend4j.galaxy.beans.ToolExecution;
import com.google.common.collect.Lists;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class TestHelpers {

  static File getTestFile() {
    return getTestFile("Hello World!!");
  }
  
  static File getTestFile(final String contents) {
    try {
      final File tempFile = File.createTempFile("galxtest", ".txt");
      final FileWriter writer = new FileWriter(tempFile);
      try {
        writer.write(contents);
      } finally {
          writer.close();
      }
      return tempFile;
    } catch(final IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
  
  static OutputDataset testUpload(final GalaxyInstance galaxyInstance, final String historyId, final File testFile) {
    final ToolsClient.FileUploadRequest request = new ToolsClient.FileUploadRequest(historyId, testFile);
    final ToolExecution execution = galaxyInstance.getToolsClient().upload(request);
    return execution.getOutputs().get(0);
  }
  
  static String getTestHistoryId(final GalaxyInstance instance) {
    return createTestHistory(instance, "blend4j Test History");
  }
  
  /**
   * Creates a new history for testing purposes.
   * @param instance  The instance of Galaxy to create a new history within.
   * @param historyName  The name of the history to create.
   * @return  The history id of the newly created history.
   */
  static String createTestHistory(final GalaxyInstance instance, String historyName) {
    final History newHistory = createTestHistoryObject(instance, historyName);
    final String historyId = newHistory.getId();
    return historyId;
  }
  
  static History createTestHistoryObject(final GalaxyInstance instance, String historyName) {
    final History testHistory = new History();
    testHistory.setName(historyName);
    final History newHistory = instance.getHistoriesClient().create(testHistory);
    return newHistory;
  }
  
  static void waitForHistory(final HistoriesClient client, final String historyId) throws InterruptedException {
    HistoryDetails details = null;
    while(true) {
      details = client.showHistory(historyId);
      if(details.isReady()) {
        break;
      }
    }
    final String state = details.getState();
    if(!state.equals("ok")) {
      for(HistoryContents content : client.showHistoryContents(historyId)) {
        if(!content.getState().equals("ok")) {
          HistoryContentsProvenance provenance = client.showProvenance(historyId, content.getId());
          final String standardError = provenance.getStandardError().replace("\n", "");
          final String message = "History no longer running, but not in 'ok' state. Found content in error with standard error " + standardError;
          throw new RuntimeException(message);
        }
      }
      final String message = "History no longer running, but not in 'ok' state. State is - " + state;
      throw new RuntimeException(message);
    }
    Thread.sleep(200L);
  }
  
  static List<String> populateTestDatasets(final GalaxyInstance instance, final String historyId, final int count) throws InterruptedException {
    final List<File> files = Lists.newArrayListWithCapacity(count);
    for(int i = 0; i < count; i++) {
      files.add(getTestFile());
    }
    return populateTestDatasets(instance, historyId, files);
  }

  static List<String> populateTestDatasets(final GalaxyInstance instance, final String historyId, final List<File> files) throws InterruptedException {
    final List<String> ids = Lists.newArrayListWithCapacity(files.size());
    for(final File file : files) {
      OutputDataset dataset = testUpload(instance, historyId, file);
      ids.add(dataset.getId());
    }
    waitForHistory(instance.getHistoriesClient(), historyId);
    return ids;
  }  

  static String populateTestDataset(final GalaxyInstance instance, final String historyId, final File file) throws InterruptedException {
    return populateTestDatasets(instance, historyId, Arrays.asList(file)).get(0);
  }

  static String populateTestDataset(final GalaxyInstance instance, final String historyId, final String contents) throws InterruptedException {
    return populateTestDatasets(instance, historyId, Arrays.asList(getTestFile(contents))).get(0);
  }

  /**
   * Compares the given files by there contents.
   * 
   * @param file1
   *          The first file.
   * @param file2
   *          The second file.
   * @return True if the files contain the same data, false otherwise.
   * @throws Exception
   *           If some error occured.
   */
  static boolean compareFileContents(File file1, File file2) throws Exception {
    byte[] bytes1 = smallFileToByteArray(file1);
    byte[] bytes2 = smallFileToByteArray(file2);

    return Arrays.equals(bytes1, bytes2);
  }

  private static byte[] smallFileToByteArray(File file) throws Exception {
    final int MAX_SIZE = 4096;

    if (file.length() > MAX_SIZE) {
      throw new Exception("Not going to read files larger than " + MAX_SIZE);
    } else {
      byte[] fileData = new byte[(int) file.length()];
      DataInputStream dis = new DataInputStream(new FileInputStream(file));
      dis.readFully(fileData);
      dis.close();

      return fileData;
    }
  }
}
