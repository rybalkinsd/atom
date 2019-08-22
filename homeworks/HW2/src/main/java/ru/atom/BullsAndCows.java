package main.java.ru.atom;

public class BullsAndCows {
    public static void main(String[] args) {
        String exit = "y";
        while ("y".equals(exit)) {
            Game game = new Game();
            game.game();
            exit=Replay.replay();
        }
    }
}
