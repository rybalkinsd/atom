package ru.atom.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.atom.model.GameServerParams;
import ru.atom.tick.Ticker;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameServerService {
    private static final Logger log = LogManager.getLogger(GameServerService.class);
    private static ConcurrentHashMap<Long, GameSession> gameSessionMap = new ConcurrentHashMap<>();
    private static final MediaType mediaType = MediaType.parse("text/html");
    private static final OkHttpClient client = new OkHttpClient();
    private static Request request;

    public static void removeGameSession(long gameId) {
        gameSessionMap.remove(gameId);
        request = new Request.Builder()
                .post(RequestBody.create(mediaType, Long.toString(gameId)))
                .url(GameServerParams.getInstance().getMatchMakerUrl() + "/matchmaker/closegs")
                .build();
        log.info(gameId);
        try {
            log.info("Game session with id =" +
                    client.newCall(request).execute().body().string() + "closed");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static long createGameSession(short playerAmount) {
        GameSession gameSession = new GameSession(playerAmount);
        log.info("Game session with " + playerAmount + " players created, returned id - " + gameSession.getId());
        gameSessionMap.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }

    public static GameSession getGameSession(long gameId) {
        return gameSessionMap.get(gameId);
    }

}
