package com.github.jmchilton.blend4j.galaxy;

import com.github.jmchilton.blend4j.galaxy.beans.History;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class TestHelpers {

  static File getTestFile() {
    try {
      final File tempFile = File.createTempFile("galxtest", ".txt");
      final FileWriter writer = new FileWriter(tempFile);
      try {
        writer.write("Hello World!!!");
      } finally {
        writer.close();
      }
      return tempFile;
    } catch(final IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
  
  static String getTestHistoryId(final GalaxyInstance instance) {
    final History testHistory = new History();
    testHistory.setName("Upload Test History");
    final History newHistory = instance.getHistoriesClient().create(testHistory);
    final String historyId = newHistory.getId();
    return historyId;
  }  
  
}
