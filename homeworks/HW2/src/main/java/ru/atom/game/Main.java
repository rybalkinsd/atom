package ru.atom.game;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.min;

/*
GAME STAGES
1 Main prints welcome text
2 Main gets random word from dictionary.txt and prints greeting with word length
3 The game asks user for guess until user wins or 10 attempts looses
4 If win, print congratulations; if loose, print loose text
5 Player is suggested to start new game
*/

public class Main {

    private static List<String> dictionary_words;

    public static void main(String[] args) {
        System.out.println("Welcome to Bulls and Cows game!");
        try {
            dictionary_words = ResourceReader.readFromResource("dictionary.txt");
        } catch (IOException e) {
            System.out.println("Sorry cant read dict :(");
            return;
        }
        while (true) {
            if (!start_session()) {
                break;
            }
        }
    }

    private static String gen_word() {
        Random random = new Random();
        return dictionary_words.get(random.nextInt(dictionary_words.size()));
    }

    private static boolean new_game_ask(Scanner in) {
        while (true) {
            System.out.println("Wanna play again? Y/N");
            System.out.print("> ");
            String ans = in.nextLine();
            if (ans.equals("Y")) {
                return true;
            } else if (ans.equals("N")) {
                return false;
            }
        }
    }

    private static boolean start_session() {
        String word = gen_word();

        System.out.println("I offered a " + word.length() + "-letter word, your guess?");

        Scanner in = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            System.out.print("> ");


            String guessWord = "";
            try {
                guessWord = in.nextLine();
                while (guessWord.length() != word.length()) {
                    System.out.println("Your guessed word is too short or too long. Try again:");
                    System.out.print("> ");
                    guessWord = in.nextLine();
                }
            } catch (NoSuchElementException e) {
                System.err.println("Error reading input");
                return false;
            }

            int bulls = 0;
            int cows = 0;
            HashSet<Character> charHashSet = new HashSet<>();
            for (int j = 0; j < guessWord.length(); j++) {
                if (guessWord.charAt(j) == word.charAt(j)) {
                    ++bulls;
                } else {
                    charHashSet.add(guessWord.charAt(j));
                }
            }

            for (Character cur : charHashSet) {
                int count1 = word.length() - word.replace(cur.toString(), "").length();
                int count2 = guessWord.length() - guessWord.replace(cur.toString(), "").length();
                cows += min(count1, count2);
            }

            if (bulls == word.length()) {
                System.out.println("You won!");
                return new_game_ask(in);
            }

            System.out.println("Bulls: " + bulls);
            System.out.println("Cows: " + cows);
        }

        System.out.println("You lose!");
        System.out.println("The word was: " + word);
        return new_game_ask(in);
    }
}
