package ru.atom;

import java.util.Scanner;

public class BullsAndCowsGame {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BullsAndCowsGame.class);

    private static boolean WinStatus;
    private static boolean continueGame = true;
    private static String guessWord = "";
    private static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {

        log.info("Game is start!");

        SecretWord secretWord = new SecretWord();
        GameJudge judge = new GameJudge();

        do {
            secretWord.guessWord();

            log.info("I guess. It's " + secretWord.getWord().length() + "-length word");
            log.info("You have 10 attempts, start =)");
            for (int i = 1; i <= 10 && !WinStatus; i++) {
                log.info("Try attempt N#" + i + ":");
                guessWord = input.nextLine();
                WinStatus = judge.check(guessWord, secretWord.getWord());
            }

            if (WinStatus) {
                log.info("You Win!!!\nDo you want to repeat? - y/n");
                suggestionPlayAgain();
            } else {
                log.info("You lose =(\nDo you try again? - y/n");
                suggestionPlayAgain();
            }

        } while (continueGame);


    }

    private static void suggestionPlayAgain() {
        WinStatus = false;
        guessWord = input.nextLine();
        continueGame = guessWord.toLowerCase().equals("y");
    }
}
