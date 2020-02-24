package CowsBulls;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CowsAndBulls {
    private static String word;
    private static int numberOfWords;

    static {
        System.out.println("Welcome to the COWS AND BULLS game");
        File fileOfWords = new File("dictionary.txt");
        numberOfWords = linesCount(fileOfWords);
        System.out.println("In total we have " + numberOfWords + " words");
    }

    private static void setting(){
        Random randomizer = new Random();
        int numOfWord = randomizer.nextInt(numberOfWords);
        File fileOfWords = new File("dictionary.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(fileOfWords))) {
            int counter = 0;
            while (true) {
                if (counter == numOfWord) {
                    word = br.readLine();
                    break;
                }
                counter += 1;
                br.readLine();
            }

            System.out.print("You need to guess a word with the length of " + word.length());
            System.out.println("\t \\\\ it's actually " + "\"" + word + "\"" + ". Under number " + (numOfWord + 1));
        } catch (IOException ioe) {
            System.out.println("IOException has occured" + ioe.getMessage());
        } catch (NullPointerException npe) {
            System.out.println("There are no words in the dictionary");
        }
    }

    private static int linesCount(File file) {
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                numberOfLines += 1;
                line = br.readLine();
            }
        } catch (IOException ioe) {
            System.out.println("IOException has occured" + ioe.getMessage());
        }
        return numberOfLines;
    }

    private static int countCows(String guess, int bullsNum) {
        try {
            if (guess.length() != word.length()) {
                System.out.println("Please enter the work with length of " + word.length());
                return -1;
            }
        } catch (NullPointerException e) {
            System.out.println("You didn't type anything!");
            return 0;
        }

        ArrayList<Integer> visited = new ArrayList<Integer>();
        int cowsNum = 0;
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < word.length(); j++) {
                if (visited.contains(j)) {
                    continue;
                }
                if (guess.charAt(i) == word.charAt(j)) {
                    cowsNum += 1;
                    visited.add(j);
                }
            }
        }
        return cowsNum - bullsNum;
    }

    private static int countBulls(String guess) {
        try {
            if (guess.length() != word.length()) {
                return -1;
            }
        } catch (NullPointerException e) {
            return 0;
        }

        int bullNum = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == word.charAt(i)) {
                bullNum += 1;
            }
        }
        return bullNum;
    }

    public static void main(String[] args) {
        String guess = null;
        Scanner in = new Scanner(System.in);

        while(true) {
            setting();
            boolean heWon = false;

            for (int i = 0; i < 3; i++) {
                guess = in.next();
                if (guess.equals(word)) {
                    System.out.println("You won!");
                    heWon = true;
                    break;
                }
                else {
                    int bulls = countBulls(guess);
                    System.out.println("Cows: " + countCows(guess, bulls));
                    System.out.println("Bulls: " + bulls);
                }
            }

            if (!heWon) {
                System.out.println("You lose");
            }
            System.out.println("Wanna play again? Y / N");
            String answer = in.next();
            boolean wannaPlayAgain = true;
            while (true) {
                if (answer.equals("N")) {
                    wannaPlayAgain = false;
                    break;
                } else if (answer.equals("Y")) {
                    break;
                } else {
                    System.out.println("Please enter \"Y\" or \"N\"");
                    answer = in.next();
                }

            }
            if (!wannaPlayAgain) {
                break;
            }
        }
    }
}
