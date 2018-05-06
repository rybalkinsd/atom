package ru.atom.game.util;

import ru.atom.game.exception.LengthMissMatchException;

public abstract class CowCounter {

    public static int getCowCount(String word, String answer) {
        if (word.length() != answer.length()) {
            throw new LengthMissMatchException("Input miss match.");
        }
        int size = word.length();
        int cows = 0;

        for (int i = 0; i < size; i++) {
            if (word.charAt(i) != answer.charAt(i)
                    && word.indexOf(answer.charAt(i)) != -1) {
                cows++;
            }
        }

        return cows;
    }
}
