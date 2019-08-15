package main.java.ru.atom;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game {
    public void game() {
        ArrayList<Character> word = new ArrayList<Character>(Word.cowsAndBulls().chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList()));

        for (int chance=10; chance > 0; chance--) {
            System.out.println("You have " + chance + "chance:");

            CorrectInput correctInput = new CorrectInput();
            ArrayList<Character> in = correctInput.correctInput(word.size());

            Bull bull = new Bull();
            bull.count(in, word);

            if (bull.getBull()==word.size()) {
                System.out.println("You win");
                break;
            }

            Cow cow = new Cow();
            cow.count(in, word);

            System.out.println("bulls:" + bull.getBull() + "cows:" + cow.getCow());

            GameOver.gameOver(word, chance);
        }
    }
}
