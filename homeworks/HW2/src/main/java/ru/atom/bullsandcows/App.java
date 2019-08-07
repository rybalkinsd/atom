package ru.atom.bullsandcows;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);

    private static List<String> getWords(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return words;
    }

    public static void main(String[] args) {
        List<String> words = getWords("dictionary.txt");
        Game game = new Game(words);
        game.start();
    }
}
