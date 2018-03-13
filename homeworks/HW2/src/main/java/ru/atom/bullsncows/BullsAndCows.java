package ru.atom.bullsncows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BullsAndCows {

    private static char[] word;
    private static char[] letters;

    private static boolean isCorrect(char[] answer) {
        if (answer == null)
            return false;
        return Arrays.equals(answer, word);
    }

    static char[] diffLetters() {
        char[] letters = new char[word.length];
        int length = 0;
        String strWord = new String(word);
        for (int i = 0; i < word.length; i++) {
            if (strWord.lastIndexOf(word[i]) == i) {
                letters[length] = word[i];
                length++;
            }
        }
        return Arrays.copyOf(letters, length);
    }


    static int numberOfCows(char[] answer) {
        int cows = 0;
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < word.length; j++) {
                if (answer[j] == letters[i]) {
                    cows++;
                    break;
                }
            }
        }

        return cows - numberOfBulls(answer);
    }

    static int numberOfBulls(char[] answer) {
        int bulls = 0;
        for (int i = 0; i < answer.length; i++) {
            if (answer[i] == word[i]) {
                bulls++;
            }
        }
        return bulls;
    }

    static void loadNewWord() {
        word = WordFinder.getWord();
        letters = diffLetters();
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        char[] answer;
        String strAnswer;

        System.out.println("Hello! This is the Bulls & Cows game!");
        loadNewWord();
        System.out.println("the secret word contains " + word.length + " letters ");

        // first step without printing of bulls and cows
        System.out.println("Make a guess: ");
        strAnswer = input.next().toLowerCase();
        //user data validating
        while (strAnswer.length() != word.length || !strAnswer.matches("[a-z]*")) {
            System.out.println("the word mast be " + word.length +
                    " letters length and contain only letters of the Latin alphabet");
            System.out.println("Please, try again: ");
            strAnswer = input.next().toLowerCase();
        }
        answer = strAnswer.toCharArray();
        int attempt = 1;

        //other steps
        while (attempt < 10 && !isCorrect(answer)) { // do-while impossible cause we don't have to print bulls&cows before
            System.out.println(answer);
            System.out.println(word);
            System.out.println(letters);
            System.out.println("cows: " + numberOfCows(answer) + "  bulls: " + numberOfBulls(answer));
            System.out.println("Make a guess: ");
            strAnswer = input.next().toLowerCase();
            while (strAnswer.length() != word.length || !strAnswer.matches("[a-z]*")) {
                System.out.println("the word mast be " + word.length +
                        " letters length and contain only letters of the Latin alphabet");
                System.out.println("Please, try again: ");
                strAnswer = input.next().toLowerCase();
            }
            answer = strAnswer.toCharArray();
            attempt++;
        }
        if (isCorrect(answer)) {
            System.out.print("You win! The correct word is ");
            System.out.println(word);
        } else {
            System.out.println("You lose :(");
        }
    }


}
