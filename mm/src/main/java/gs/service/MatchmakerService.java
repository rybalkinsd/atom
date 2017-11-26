package gs.service;

import gs.network.MatchmakerClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchmakerService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MatchmakerService.class);

    @Autowired
    MatchmakerClient client;

    private static final String URI = "localhost:8090/game/";
    private static final int PLAYER_COUNT = 2;
    private long gameId;
    private int vacantPlaces = 0;

    public long join(@NotNull String name) {
        if (vacantPlaces != 0) {
            vacantPlaces--;
            logger.info(name + " joined to game: " + gameId);
            return gameId;
        } else {
            gameId = Long.parseLong(client.createPost(PLAYER_COUNT));
            vacantPlaces = PLAYER_COUNT - 1;
            return gameId;
        }
    }
}
