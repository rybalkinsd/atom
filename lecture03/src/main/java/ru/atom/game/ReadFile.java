package ru.atom.game;

import ru.atom.exception.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandler.class);
    private static List<String> fileData = new ArrayList<>();

    private static List<String> readAllFromFile(String filename) {
        File file = new File(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                fileData.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException ex) {
            log.warn("FileNotFoundException in readOneLineFromFile, filename = {}", filename);
            return null;
        } catch (IOException ex) {
            log.warn("IOException in readOneLineFromFile, filename = {}", filename);
        }
        return fileData;
    }

    static String getRandomValue() {
        Random random = new Random();
        return readAllFromFile("./src/main/resources/dictionary.txt").get(random.nextInt(fileData.size()));
    }
}