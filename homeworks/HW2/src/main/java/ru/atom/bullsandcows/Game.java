package ru.atom.bullsandcows;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements Bulls and Cows game logic
 */
public class Game {

    private static final Random random = new Random();
    private List<String> words;
    private String secretWord;

    public Game(List<String> words) {
        this.words = words;
    }

    private String chooseSecretWord() {
        return words.get(random.nextInt(words.size()));
    }

    /**
     * Start game session
     */
    public void start() {
        System.out.println("Welcome to Bulls and Cows game!");
        String playAgain = "Y";
        Scanner scanner = new Scanner(System.in);
        do {
            secretWord = chooseSecretWord();
            System.out.println("I offered a " + secretWord.length() + "-letter word, your guess?");
            playRound(scanner);
            System.out.println("Wanna play again? Y/N");
            playAgain = scanner.next();
        } while (playAgain.equalsIgnoreCase("Y"));
    }

    /**
     * Play one word game
     */
    private void playRound(Scanner scanner) {
        int losses = 0;
        while (losses < 10) {
            String guess = scanner.next();
            if (guess.length() != secretWord.length()) {
                System.out.println("Wrong length guess. Try again.");
                continue;
            }
            if (guess.equals(secretWord)) {
                System.out.println("You won!");
                return;
            }
            System.out.println("Bulls: " + computeBulls(guess));
            System.out.println("Cows: " + computeCows(guess));
            losses++;
        }
        System.out.println("You lose :(");
    }

    /**
     * Computes number of same chars in corresponding positions in guess and secret
     * @return number of same chars in corresponding positions in guess and secret
     */
    private int computeBulls(String guess) {
        int bulls = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    /**
     * Computes number of same chars in any positions in guess and secret
     * @return number of same chars in any positions in guess and secret
     */
    private int computeCows(String guess) {
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            int pos = secretWord.indexOf(guess.charAt(i));
            if (pos != -1 && pos != i) {
                cows++;
            }
        }
        return cows;
    }
}
