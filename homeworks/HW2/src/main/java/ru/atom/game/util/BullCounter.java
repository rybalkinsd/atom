package ru.atom.game.util;

import ru.atom.game.exception.LengthMissMatchException;

public abstract class BullCounter {
    public static int getBullCount(String word, String answer) {
        if (word.length() != answer.length()) {
            throw new LengthMissMatchException("Input miss match.");
        }
        int size = word.length();
        int bulls = 0;

        for (int i = 0; i < size; i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                bulls++;
            }
        }

        return bulls;
    }
}
