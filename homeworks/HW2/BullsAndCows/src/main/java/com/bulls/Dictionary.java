package com.bulls;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
    private ArrayList<ArrayList<Character>> allWords;

    Dictionary() {
        allWords = new ArrayList<>();
    }

    void init() {
        try(FileReader reader = new FileReader("C:\\Users\\Dev\\Desktop\\LinFolder\\java\\atom\\homeworks\\HW2\\BullsAndCows\\dictionary.txt"))
        {
            int c;
            allWords.add(new ArrayList<>());

            while((c=reader.read())!=-1){
                if((char) c != '\n') {
                    allWords.get(allWords.size() - 1).add((char) c);
                } else {
                    allWords.add(new ArrayList<>());
                }
            }
        }
        catch(IOException ex){
            allWords = null;
        }
    }
    public ArrayList<Character> getWord() {
        if(allWords == null) {
            ArrayList<Character> randWord = new ArrayList<>();
            for(int i = 0; i < 4; i ++)
                randWord.add((char)('a' + ('z' - 'a') * Math.random()));
            return randWord;
        }
        return new ArrayList<>(allWords.get((int)(Math.random() * allWords.size())));
    }
}
