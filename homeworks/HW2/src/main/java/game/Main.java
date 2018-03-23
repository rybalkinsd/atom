package game;

import files.ResourceReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("log4j2.properties");
    public static void main (String args[]) throws IOException {
        List<String> words = ResourceReader.readFromResource("dictionary.txt");
        System.out.println("Welcome to Bulls and Cows game!");
        String word = newWord(words);

        log.info(word);
        Scanner s = new Scanner(System.in);

        while (true) {
            String guess = s.nextLine();
            if(guess.equals(word)) {
                System.out.println("You won!");
                System.out.println("Wanna play again?(y/n)");
                char q = s.nextLine().charAt(0);
                if(q == 'n') {
                    break;
                } else {
                    word = newWord(words);
                }
            } else {
                getHint(word, guess);
            }
        }
    }

    public static void getHint(String word, String guess) {
        int bulls = 0;
        int cows = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        //Bulls
        for(int i=0; i<word.length(); i++) {
            char c1 = word.charAt(i);
            char c2 = guess.charAt(i);

            if(c1 == c2) {
                bulls++;
            } else {
                if(map.containsKey(c1)) {
                    int freq = map.get(c1);
                    freq++;
                    map.put(c1,freq);
                } else {
                    map.put(c1,1);
                }
            }
        }
        //Cows
        for(int i=0; i<word.length(); i++) {
            char c1 = word.charAt(i);
            char c2 = guess.charAt(i);

            if(c1!=c2) {
                if(map.containsKey(c2)) {
                    cows++;
                    if(map.get(c2) == 1) {
                        map.remove(c2);
                    } else {
                        int freq = map.get(c2);
                        freq--;
                        map.put(c2, freq);
                    }
                }
            }
        }
        System.out.println("Bulls: " + bulls + "\nCows: " + cows);
    }
    public static String newWord(List<String> words) {
        Random random = new Random();
        String word = words.get(random.nextInt(words.size()));
        System.out.println("I offered a " + word.length() + "-letter word, your guess?");
        return word;
    }
}
