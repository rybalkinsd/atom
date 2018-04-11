package mm.Scheduled;

import mm.Repo.GameSession;
import mm.Repo.GameSessionsRepository;
import mm.Service.MatchMaker;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class IdleGamesCheck {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    @Autowired
    private GameSessionsRepository gameSessionsRepository;

    @Scheduled(fixedRate = 30000)
    public void checkIdleGames() {
        log.info("Idle games check");
        ArrayList<GameSession> idleSessions = gameSessionsRepository.returnIdleSessions();
        Date now = new Date();
        for (GameSession game : idleSessions) {
            if ((now.getTime() - game.getTimeOfLastAction().getTime()) > 25000) {
                try {
                    start(game.getId());
                } catch (IOException e) {
                    log.info("Scheduled GameSession {} failed to start", game.getId());
                }
            }
        }
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
