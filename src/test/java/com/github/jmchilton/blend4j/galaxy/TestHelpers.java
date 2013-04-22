package com.github.jmchilton.blend4j.galaxy;

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
}
