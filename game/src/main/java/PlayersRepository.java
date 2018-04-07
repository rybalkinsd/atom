import java.util.ArrayList;

public class PlayersRepository {
    private ArrayList<Player> playersRegistered = new ArrayList<>();

    public void add(Player player) {
        playersRegistered.add(player);
    }

    public Player get(String name) throws NoSuchFieldException {
        for (Player player: playersRegistered) {
            if (player.getName().equals(name))
                return player;
        }
        throw new NoSuchFieldException("Player " + name + "not registered");
    }


}
