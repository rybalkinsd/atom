package com.bulls;

public class Main {

    public static final String PATH_TO_DICTIONARY = "dict/dictionary.txt";

    public static void main(String[] args) {
        BullsAndCowsGame game = new BullsAndCowsGame();
        game.play();
    }
}
