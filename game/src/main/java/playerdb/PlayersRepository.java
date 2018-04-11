package playerdb;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Component
public class PlayersRepository {
    private ArrayList<Player> playersRegistered = new ArrayList<>();

    @Bean
    public static PlayersRepository createPlayersRepository() {
        return new PlayersRepository();
    }

    public void add(Player player) {
        playersRegistered.add(player);
    }

    public Player get(String name) throws NoSuchFieldException {
        for (Player player: playersRegistered) {
            if (player.getName().equals(name))
                return player;
        }
        throw new NoSuchFieldException("Player " + name + " not registered");
    }


}
