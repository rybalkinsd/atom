package main.java.ru.atom;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
    public void game() {
        for (int chance = 10; chance > 0; chance--) {
            ArrayList<Character> word = new ArrayList<Character>(Word.cowsAndBulls().chars()
                                                                 .mapToObj(e -> (char) e)
                                                                 .collect(Collectors.toList()));
            
            System.out.println("You have " + chance + "chance:");

            CorrectInput correctInput = new CorrectInput();
            ArrayList<Character> in = correctInput.correctInput(word.size());
            
            Bull bull = new Bull();
            bull.count(in, word);

            Cow cow = new Cow();
            cow.count(in, word);

            System.out.println("bulls:" + bull.getBull() + "cows:" + cow.getCow());

            GameOver gameOver = new GameOver();
            gameOver.gameOver(word, chance);
        }
    }
}
