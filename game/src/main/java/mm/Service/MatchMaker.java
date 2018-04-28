package mm.Service;


import mm.Repo.GameSession;
import mm.Repo.GameSessionsRepository;
import mm.playerdb.dao.Player;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MatchMaker {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static final int RATINGRANGE = 500;
    private static final int maxPlayersInSession = 4;

    /* need to add logging to database*/
    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    public static MatchMaker getInstance() {
        return new MatchMaker();
    }

    @Autowired
    private GameSessionsRepository gameSessionsRepository;

    public GameSession getSession(Player currentPlayer) throws IOException {
        int ratingRange = 30;
        GameSession result = null;
        while (((result = gameSessionsRepository.get(
                currentPlayer.getRating() - ratingRange,
                currentPlayer.getRating() + ratingRange)) == null)
                && (ratingRange < RATINGRANGE)) {
            ratingRange += 10;
        }
        if (result == null) {
            result = gameSessionsRepository.get(create());
        }
        result.add(currentPlayer);
        if (result.isFull()) {
            start(result.getId());
        }
        log.info(gameSessionsRepository.toString());
        return result;
    }

    /*returns ID of a created game*/
    private long create() throws IOException {
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount=" + maxPlayersInSession))
                .url(PROTOCOL + HOST + PORT + "/game/create")
                .build();
        Response response = client.newCall(request).execute();
        return  Long.parseLong(response.body().string());
    }

    private void connect(long gameId,String name) throws IOException {
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,
                        "gameId=" + gameId + "&name=" + name))
                .url(PROTOCOL + HOST + PORT + "/game/connect")
                .build();
        client.newCall(request).execute();
    }

    private void start(long gameId) throws IOException {
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId=" + gameId))
                .url(PROTOCOL + HOST + PORT + "/game/start")
                .build();
        client.newCall(request).execute();
    }
}
