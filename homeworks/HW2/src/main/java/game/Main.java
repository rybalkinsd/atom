package game;

import files.ResourceReader;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("log4j2.properties");
    public static void main (String args[]) throws IOException {
        System.out.println("Welcome to Bulls and Cows game!");


        Random random = new Random();
        List<String> words = ResourceReader.readFromResource("dictionary.txt");
        String word = words.get(random.nextInt(words.size()));
        System.out.println("I offered a " + word.length() + "-letter word, your guess?");
        Scanner s = new Scanner(System.in);
        String guess = s.nextLine();

        while (!guess.equals(word)) {
            int cows = 0;
            int bulls = 0;

            for (int i=0;i<word.length();i++) {
                if(word.charAt(i) == guess.charAt(i)) {
                    bulls++;
                } else {
                    //TODO: cows add
                }
            }

            System.out.println("Bulls :" + bulls);
            System.out.println("Cows: " + cows);
            guess = s.nextLine();
        }
        System.out.println("You won!");
    }
}
