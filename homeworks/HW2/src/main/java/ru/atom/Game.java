package main.java.ru.atom;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
    private int chance;

    private ArrayList<Character> in;
    private ArrayList<Character> word = new ArrayList<Character>(Word.cowsAndBulls().chars()
            .mapToObj(e -> (char) e)
            .collect(Collectors.toList()));

    public void game() {
        chance = 10;

        for (; chance > 0; chance--) {
            System.out.println("You have " + chance + "chance:");

            CorrectInput correctInput = new CorrectInput();
            in = correctInput.correctInput(word.size());
            
            Bull bull = new Bull();
            bull.count(in, word);

            Cow cow = new Cow();
            cow.count(in, word);

            System.out.println("bulls:" + bull.getBull() + "cows:" + cow.getCow());

            GameOver gameOver = new GameOver();
            gameOver.gameOver(word, bull.getBull(), chance);

            Winner winner = new Winner();
            chance = winner.winner(word.size(), bull.getBull(), chance);
        }
    }
}
