package ru.atom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Words {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Words.class);
    private static final String DICT_DIR = "src/main/resources/dictionary.txt";
    ArrayList<String> dictionary = new ArrayList<>();

    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DICT_DIR))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                dictionary.add(line);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }
}
