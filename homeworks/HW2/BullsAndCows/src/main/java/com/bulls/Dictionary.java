package com.bulls;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Dictionary {
    private ArrayList<ArrayList<Character>> allWords;

    Dictionary() {
        allWords = new ArrayList<>();
    }

    // load words from dictionary
    // loading little bit long
    void init() {
        try (InputStream reader = getDictionary()) {
            if (reader == null) {
                allWords = null;
                return;
            }
            int symbol;
            allWords.add(new ArrayList<>());

            while ((symbol = reader.read()) != -1) {
                if ((char) symbol != '\n')
                    allWords.get(allWords.size() - 1).add((char) symbol);
                else
                    allWords.add(new ArrayList<>());

            }
        } catch (IOException e) {
            if (allWords != null && allWords.size() == 0)
                allWords = null;
        }
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
