package ru.atom.lecture09.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class CharacterStreams {
    @Test
    public void writeFile() throws IOException {
        try (PrintWriter writer = new PrintWriter("src/main/resources/to.txt", "UTF-8")) {
            writer.println("The first line by writer");
            writer.println("The second line by writer");
            writer.close();
        }
    }

    @Test
    public void readFile() throws IOException {
        try (InputStream in = new FileInputStream(new File("src/main/resources/to.txt"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))
        ) {
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());
        }
    }
}
