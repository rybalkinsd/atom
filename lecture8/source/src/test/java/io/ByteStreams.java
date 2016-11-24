package io;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class ByteStreams {
  @Test
  public void fileWrite() throws IOException {
    try (FileOutputStream out = new FileOutputStream("src/main/resources/to.txt")) {
      out.write("To be or not to be".getBytes());
    }
  }

  @Test
  public void fileRead() throws IOException {
    try (FileInputStream in = new FileInputStream("src/main/resources/to.txt")) {
      int content;
      while ((content = in.read()) != -1) {
        System.out.print((char) content);
      }
    }
  }

  @Test
  public void fileReadWrite() throws IOException {
    try (FileInputStream in = new FileInputStream("src/main/resources/from.txt");
        FileOutputStream out = new FileOutputStream("src/main/resources/to.txt");
    ) {
      int c;
      while ((c = in.read()) != -1) {
        out.write(c);
      }
    }
  }
}
