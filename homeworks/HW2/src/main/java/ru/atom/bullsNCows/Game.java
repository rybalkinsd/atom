import java.util.ArrayList;
import java.util.Random;

/**
 * Created by User on 12.03.2018.
 * sphere
 */
class Game {
    static void game(ArrayList<String> words) {
        boolean nextGame = true;
        Random wordNGenerator = new Random();
        int wordN;

        System.out.println("Welcome to the Bulls and Cows Game!");
        while (nextGame) {
            wordN = wordNGenerator.nextInt(words.size());
            String currentWord = words.get(wordN);
            System.out.println("I offered a " + currentWord.length() + "-letter word, your guess?");
            Checker.game(currentWord);
            System.out.println("Wanna play again? Y/N");
            nextGame = Checker.exitCheck();
        }
    }

}
