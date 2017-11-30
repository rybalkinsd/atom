package mm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class Matchmaker {
    private static final Logger log = LogManager.getLogger(Matchmaker.class);

    @Autowired
    GameService client;
    private static final int PLAYER_COUNT = 2;
    private long gameId;
    private int playersInGame = 0;

    public long join(@NotNull String name) {
        if (playersInGame != 0) {
            //gameId = Long.parseLong(client.start(PLAYER_COUNT));
            return gameId;
        } else {
            //gameId = Long.parseLong(client.create(PLAYER_COUNT));
            playersInGame = playersInGame + 1;
            return gameId;
        }
    }
}
