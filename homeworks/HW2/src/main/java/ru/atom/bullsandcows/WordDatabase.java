package ru.atom.bullsandcows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDatabase {
    private final List<String> words;
    private final Random rng = new Random();

    public WordDatabase(String dictionary) throws IOException {
        InputStream inputStream = WordDatabase.class.getClassLoader().getResourceAsStream(dictionary);
        words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
    }

    public String getRandomWord() {
        return words.get(rng.nextInt(words.size()));
    }

}
