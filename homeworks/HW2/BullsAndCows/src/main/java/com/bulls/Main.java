package com.bulls;

public class Main {

    public static final String PATH_TO_DICTIONARY = "dict/dictionary.txt";

    public static void main(String[] args) {
        System.out.println(Main.class.getClassLoader().getResourceAsStream(PATH_TO_DICTIONARY));
        ClassLoader classLoader = Main.class.getClassLoader();
        System.out.println(classLoader.getResourceAsStream("dict/dictionary.txt"));

        BullsAndCowsGame game = new BullsAndCowsGame();
        game.play();
    }

}
