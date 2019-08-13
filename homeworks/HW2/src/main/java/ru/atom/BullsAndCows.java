package main.java.ru.atom;

public class BullsAndCows {
    public static void main(String[] args) {
        String exit = "y";
        while (exit.equals("y")) {
            Game game = new Game();
            game.game();
            exit=Replay.replay();
        }
    }
}
