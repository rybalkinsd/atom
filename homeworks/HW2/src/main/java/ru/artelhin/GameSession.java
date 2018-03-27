package ru.artelhin;

import java.util.ArrayList;
import java.util.Random;

public class GameSession {
    private static int won = 0;
    private static int lost = 0;

    public static void handleGame(ArrayList<String> list) {
        String word;
        String answer;
        boolean nextParty = true;
        boolean winner = false;
        int index;
        int wordLength;
        Random num = new Random();

        System.out.println("Welcome to the Bulls and Cows Game!");
        while (nextParty) {
            index = num.nextInt(list.size());
            word = list.get(index);
            wordLength = word.length();
            WordChecker.patternSet(wordLength);
            System.out.println("I offered a " + wordLength + "-letter word. Your guess?");
            for (int i = 0; i < 10; i++) {
                answer = UserInput.wordInput();
                if (WordChecker.wordCompare(word, answer)) {
                    winner = true;
                    break;
                }
            }
            if (winner) {
                System.out.println("Congratulations! You won!");
                won++;
            } else {
                System.out.println("You lost!");
                lost++;
            }
            System.out.println("Your current session statistics is: " + won + " WON " + lost + " LOST");
            System.out.println("Wanna play once again? Y/N");
            nextParty = UserInput.answerInput();

        }
    }

}
