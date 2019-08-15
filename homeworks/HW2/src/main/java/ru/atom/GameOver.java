package main.java.ru.atom;

import java.util.ArrayList;

public class GameOver {
    public void gameOver(ArrayList word,int bull,int chance) {
        if (chance == 1 && bull != word.size()) {
            System.out.println("Game over");
            System.out.print("Answer:");
            word.forEach(o -> System.out.print(o));
            System.out.println("");
        }
    }
}
