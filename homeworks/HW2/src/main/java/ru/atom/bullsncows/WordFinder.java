package ru.atom.bullsncows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WordFinder {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("LOGGER_NAME");
    private static final String pathStr = "./src/main/resources/dictionary.txt";
    private static final Path path = Paths.get(pathStr);

    static char[] getWord() {
        try {
            long lineCount = Files.lines(path).count();
            long rndLine = (long) Math.ceil(Math.random() * lineCount);
            String word = Files.lines(path).skip(rndLine - 1).findFirst().get();
            return word.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(pathStr + " no such file!");
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getWord());
    }
}
