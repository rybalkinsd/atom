package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {
    private int Handle_bulls(char[] word_ar, char[] guess_word_ar, List<Character> word_list, List<Character> guess_word_list) {
        int bulls = 0;
        for (int i = 0; i < Math.min(word_ar.length, guess_word_ar.length); i++) {
            if (word_ar[i] == guess_word_ar[i]){
                bulls++;
            } else {
                word_list.add(word_ar[i]);
                guess_word_list.add(guess_word_ar[i]);
            }
        }
        return bulls;
    }

    private int Handle_cows(List<Character> word_list, List<Character> guess_word_list) {
        int cows = 0;
        for (char c : guess_word_list) {
            for (int i = 0; i < word_list.size(); i++) {
                if (c == word_list.get(i)) {
                    cows++;
                    word_list.remove(i);
                    i--;
                    break;
                }
            }
        }
        return cows;
    }
    private void Give_hint(String word, String guess_word){
        char[] word_ar = word.toCharArray();
        char[] guess_word_ar = guess_word.toCharArray();
        List<Character> word_list = new ArrayList<Character>();
        List<Character> guess_word_list = new ArrayList<Character>();
        int bulls = Handle_bulls(word_ar, guess_word_ar, word_list, guess_word_list);
        int cows = Handle_cows(word_list, guess_word_list);
        System.out.println("Bulls: " + bulls);
        System.out.println("Cows: " + cows);

    }

    private String Get_random_word(List<String> lines){
        int size = lines.size();
        Random rnd = new Random();
        int index = rnd.nextInt(size-1);
        return lines.get(index);
    }
    public void Run() throws IOException {
        boolean play = true;
        boolean won = false;
        while (play) {
            List<String> lines = ResourceReader.readFromResource("dictionary.txt");
            String word = Get_random_word(lines);
            //String word = "java";
            System.out.println("Welcome to Bulls and Cows game!");
            System.out.println("I offered a " + word.length() + "-letter word, your guess?");
            Scanner sc = new Scanner(System.in);

            for (int i = 0; i < 10; i++) {
                String guess_word = sc.nextLine();
                if (guess_word.equals(word)) {
                    won = true;
                    System.out.println("You won!");
                    break;
                }
                Give_hint(word, guess_word);
            }
            if (!won) System.out.println("You lose:(");
            System.out.println("Wanna play again? Y/N");
            String user_ans = sc.nextLine();
            if (user_ans.length() > 1 || user_ans.charAt(0) != 'Y') play = false;
        }

    }

    public static void main(String[] args) throws IOException {
        BullsAndCows new_game = new BullsAndCows();
        new_game.Run();
    }
}
