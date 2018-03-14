public class BCMain {
    public static void main(String[] args) {
        BCInterface cur_game = new BCInterface();
        do {
            cur_game.play();
        } while (cur_game.active());
    }
}