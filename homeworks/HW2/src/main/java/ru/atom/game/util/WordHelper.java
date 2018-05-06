package ru.atom.game.util;

import ru.atom.game.exception.ResourceReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class WordHelper {
    private static List<String> words = null;
    private static Random random = new Random();
    private static final String fileName = "dictionary.txt";

    public static String getRandomWord() {
        if (words == null) {
            try {
                fillWordList();
            } catch (IOException exc) {
                throw new ResourceReadException("Can't read " + fileName + ".");
            }
        }
        return words.get(random.nextInt(words.size()));
    }

    private static void fillWordList() throws IOException {
        InputStream inputStream = WordHelper.class.getClassLoader().getResourceAsStream(fileName);
        words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
    }
}
