package ru.atom.lecture09.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
            int chunk;
            while ((chunk = in.read()) != -1) {
                out.write(chunk);
            }
        }
    }
}
