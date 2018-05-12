package ru.atom.game;

import java.util.HashMap;
import java.util.Scanner;

public class BullsAndCows {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BullsAndCows.class);
    private static Scanner scanner = null;

    public static void main(String... args) {
        String answer;
        int allowedAttempts = 10;
        do {
            int usedAttempts = 0;
            boolean winner = false;
            String word = ReadFile.getRandomValue();
            int length = word.length();
            log.info("Welcome to Bulls and Cows game!");
            log.info("I offered " + length + "-letter word, your guess?");
            do {
                usedAttempts++;
                scanner = new Scanner(System.in);
                String guess = checkInput(scanner.nextLine(), length);
                int cowsCount = checkBullsAndCows(word, guess).getCows();
                int bullsCount = checkBullsAndCows(word, guess).getBulls();
                log.info("You have: " + "\n Cows - " + cowsCount
                        + "\n Bulls - " + bullsCount);
                if (bullsCount == length) {
                    log.info("Our congratulations! You are a winner!");
                    winner = true;
                    break;
                }
            } while (!winner && usedAttempts < allowedAttempts);
            if (!winner) {
                log.info("Sorry, you loose.");
            }
            log.info("Do you want to try again? (Y/N)?");
            answer = scanner.nextLine().toLowerCase();

            while (!answer.equals("y") && !answer.equals("n")) {
                log.warn("Please input only \"Y\" or \"N\" letters for answer.");
                answer = scanner.nextLine().toLowerCase();
            }

        } while (!answer.equals("n"));
        log.info("Thank you for playing ;-)");
    }

    private static String checkInput(String value, int length) {
        String text = value;

        while (!text.matches("[a-zA-Z]+") || text.length() != length) {
            log.warn("Please input only " + length + " letters!");
            text = scanner.nextLine();
        }
        return text;
    }

    private static BullsAndCowsValueObject checkBullsAndCows(String word, String guess) {
        int bullsCount = 0;
        int cowsCount = 0;

        HashMap<Character, Integer> map = new HashMap<>();

        //check bulls
        for (int i = 0; i < word.length(); i++) {
            char c1 = word.charAt(i);
            char c2 = guess.charAt(i);

            if (c1 == c2) {
                bullsCount++;
            } else {
                if (map.containsKey(c1)) {
                    int freq = map.get(c1);
                    freq++;
                    map.put(c1, freq);
                } else {
                    map.put(c1, 1);
                }
            }
        }

        //check cows
        for (int i = 0; i < word.length(); i++) {
            char c1 = word.charAt(i);
            char c2 = guess.charAt(i);

            if (c1 != c2) {
                if (map.containsKey(c2)) {
                    cowsCount ++;
                    if (map.get(c2) == 1) {
                        map.remove(c2);
                    } else {
                        int freq = map.get(c2);
                        freq--;
                        map.put(c2, freq);
                    }
                }
            }
        }

        return new BullsAndCowsValueObject(bullsCount, cowsCount);
    }

    private static class BullsAndCowsValueObject {
        private int bulls;
        private int cows;

        BullsAndCowsValueObject(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }

        public int getBulls() {
            return bulls;
        }

        public int getCows() {
            return cows;
        }
    }
}