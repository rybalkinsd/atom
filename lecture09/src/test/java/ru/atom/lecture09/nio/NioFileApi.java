package ru.atom.lecture09.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class NioFileApi {
    @Test
    public void writeFile() throws IOException {
        List<String> lines = Arrays.asList(
                "The first line by NioFileApi",
                "The second line by NioFileApi"
        );
        Path file = Paths.get("src/main/resources/to.txt");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    @Test
    public void readFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/to.txt"));
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            sb.append(s);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
