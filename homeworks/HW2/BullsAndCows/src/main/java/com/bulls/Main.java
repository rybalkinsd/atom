package com.bulls;

public class Main {

    public static final String PATH_TO_DICTIONARY = "dict/dictionary.txt";

    public static void main(String[] args) {
        System.out.println(Main.class.getClassLoader().getResourceAsStream(PATH_TO_DICTIONARY));
        ClassLoader classLoader = Main.class.getClassLoader();
        System.out.println(classLoader.getResourceAsStream("dict/dictionary.txt"));

        setOs();
        BullsAndCowsGame game = new BullsAndCowsGame();
        game.play();
    }

    //windows
    private static boolean isWindows(String os) {
        return (os.contains("win"));
    }

    //Mac
    private static boolean isMac(String os) {
        return (os.contains("mac"));

    }

    //linux or unix
    private static boolean isUnix(String os) {
        return (os.contains("nix") || os.contains("nux"));
    }
}
