package ru.atom;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by imakarycheva on 12.03.18.
 */
public class BullsAndCows {

    private List<String> words;
    private static final Logger log = LoggerFactory.getLogger(BullsAndCows.class);
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        new BullsAndCows().run();
    }

    public void run() {
        System.out.println("Welcome to Bulls and Cows! Let's play!");
        if (init()) {
            do {
                playOneRound();
            } while (doesPlayerWantPlayMore());
        } else {
            System.out.println("Sorry, I cannot play with you now. Please contact to my creator.");
        }
    }

    private boolean init() {
        try {
            words = ResourceReader.readFromResource("dictionary.txt");
        } catch (IOException e) {
            log.error("Could not read words from dictionary.txt");
            log.error(e.getMessage(), e.getStackTrace());
            return false;
        }
        if (words.isEmpty()) {
            log.error("Probably dictionary.txt is empty");
            return false;
        }
        return true;
    }

    private void playOneRound() {
        String secret = words.get(random.nextInt(words.size())).toLowerCase();
        System.out.println("I have a " + secret.length() + "-letter word. Try to guess it.");
        String guess;
        for (int i = 0; i < 10; i++) {
            guess = getUsersGuess(secret.length()).toLowerCase();
            Set<Integer> bulls = new HashSet<>(secret.length());
            for (int j = 0; j < secret.length(); j++) {
                if (secret.charAt(j) == guess.charAt(j)) {
                    bulls.add(j);
                }
            }
            if (bulls.size() == secret.length()) {
                System.out.println("You won! Congratulations!");
                return;
            } else {
                Set<Integer> cows = new HashSet<>(secret.length());
                for (int j = 0; j < guess.length(); j++) {
                    for (int k = 0; k < secret.length(); k++) {
                        if (bulls.contains(k) || cows.contains(k)) {
                            continue;
                        } else if (secret.charAt(k) == guess.charAt(j)) {
                            cows.add(k);
                            break;
                        }
                    }
                }
                System.out.println("You have " + bulls.size() + " bulls and " + cows.size() + " cows.");
            }
        }
        System.out.println("You lost!");
    }

    private boolean doesPlayerWantPlayMore() {
        System.out.println("Wanna play one more time? (y/n)");
        String answer;
        while (true) {
            answer = scanner.nextLine().toLowerCase();
            if (answer.startsWith("y")) {
                return true;
            } else if (answer.startsWith("n")) {
                return false;
            } else {
                System.out.println("Sorry, I did not understand. Do you want to play one more time? (y/n)");
            }
        }
    }

    private String getUsersGuess(int wordLength) {
        String guess = scanner.nextLine();
        while (true) {
            if (guess.length() != wordLength) {
                System.out.println("Please type a " + wordLength + "-letter word.");
                guess = scanner.nextLine();
            } else {
                return guess;
            }
        }
    }
}
