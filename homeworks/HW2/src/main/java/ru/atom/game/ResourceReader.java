package ru.atom.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceReader {
    public static List<String> readFromResource(String resourceName)
            throws IOException {
        InputStream inputStream;
        inputStream = ru.atom.game.ResourceReader.class.getClassLoader().getResourceAsStream(resourceName);
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}