import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by User on 12.03.2018.
 * sphere
 */
public class Checker {
    private static Scanner input = new Scanner(System.in);
    private static String usrWord;
    private static int len = 0;
    //private static boolean win = false;

    private static boolean wordCheck() {
        System.out.print("> ");
        usrWord = input.next();
        return !(usrWord.toLowerCase().matches(".*[^a-z]+.*") || usrWord.length() != len);
    }

    static void game(String patternWord) {
        int attempts = 0;
        boolean win = false;
        while (!win && attempts < 10) {
            if (!(win = check(patternWord))) {
                attempts++;
            }
        }
        if (win) {
            System.out.println("You won!");
            return;
        }
        System.out.println("You loose... 10 attempts were made");
        System.out.println("Word was " + patternWord);
    }

    static boolean exitCheck() {
        try {
            usrWord = input.next();
        } catch (Exception e) {
            System.out.println("Something wrong with user input");
            System.out.println(e.getMessage());
            return false;
        }
        if (usrWord.equalsIgnoreCase("Y")) {
            return true;
        }
        if (usrWord.equalsIgnoreCase("N")) {
            return false;
        }
        System.out.println("Input y or n");
        return exitCheck();
    }

    private static boolean check(String patternWord) {
        len = patternWord.length();
        int bulls = 0;
        int cows = 0;
        try {
            while (!wordCheck()) {
                System.out.println("Word must contains only letters and word len must be " + len);
            }
        } catch (Exception e) {
            System.out.println("Something wrong with user input");
            System.out.println(e.getMessage());
            return false;
        }
        if (patternWord.equalsIgnoreCase(usrWord)) {
            System.out.println("You won!");
            return true;
        }
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < len; i++) {
            char patternLetter = patternWord.charAt(i);
            char usrLetter = usrWord.charAt(i);
            if (patternLetter == usrLetter) {
                bulls++;
            } else {
                if (map.containsKey(patternLetter)) {
                    map.put(patternLetter,map.get(patternLetter) + 1);
                } else {
                    map.put(patternLetter, 1);
                }
            }
        }

        for (int i = 0; i < len; i++) {
            char patternLetter = patternWord.charAt(i);
            char usrLetter = usrWord.charAt(i);

            if (patternLetter != usrLetter && map.containsKey(usrLetter)) {
                cows++;
                if (map.get(usrLetter) == 1) {
                    map.remove(usrLetter);
                } else {
                    map.put(usrLetter,map.get(usrLetter) - 1);
                }
            }
        }
        System.out.println("Bulls: " + bulls);
        System.out.println("Cows: " + cows);
        return false;
    }
}
