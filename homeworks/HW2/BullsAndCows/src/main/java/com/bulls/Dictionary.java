package com.bulls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Dictionary {
    private ArrayList<ArrayList<Character>> allWords;
    private static final Logger log = LoggerFactory.getLogger(Dictionary.class);

    Dictionary() {
        allWords = new ArrayList<>();
    }

    // load words from dictionary
    boolean init() {
        try (InputStream reader = getDictionary()) {
            if (reader == null) {
                allWords = null;
                log.error("Could not find dictionary.txt;");
                return false;
            }
            int symbol;
            allWords.add(new ArrayList<>());

            while ((symbol = reader.read()) != -1) {
                if ((char) symbol != '\n')
                    allWords.get(allWords.size() - 1).add((char) symbol);
                else
                    if(allWords.get(allWords.size() - 1).size() != 0)
                        allWords.add(new ArrayList<>());
            }
            if(allWords.get(allWords.size() - 1).size() == 0)
                allWords.remove(allWords.size() - 1);
        } catch (IOException e) {
            log.error("Could not read words from dictionary.txt;");
            allWords = null;
        }
        if (allWords != null && allWords.size() == 0) {
            log.error("Dictionary is empty.");
            allWords = null;
        }
        return allWords != null;
    }

    // here we get dictionary as resource
    private InputStream getDictionary() {
        return Dictionary.class.getClassLoader().getResourceAsStream(Main.PATH_TO_DICTIONARY);
    }

    // get random word from dictionary
    // or if we didn`t init it we generate random word
    public ArrayList<Character> getWord() {
        if (allWords == null) {
            ArrayList<Character> randWord = new ArrayList<>();
            for (int i = 0; i < 4; i ++)
                randWord.add((char)('a' + ('z' - 'a') * Math.random()));
            return randWord;
        }
        return new ArrayList<>(allWords.get((int)(Math.random() * allWords.size())));
    }
}
