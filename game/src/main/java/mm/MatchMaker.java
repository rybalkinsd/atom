package mm;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.io.IOException;

@Controller
@RequestMapping("/matchmaker")
public class MatchMaker {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    private static final int maxPlayersInSession = 4;

    /* need to add logging to database*/
    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    @Autowired
    private GameSessionsRepository gameSessionsRepository = GameSessionsRepository.createGameSessionsRepository();

    @Autowired
    private PlayersRepository playersRepository = PlayersRepository.createPlayersRepository();

    /* After pressing 'START' button Matchmaker puts player in a game session via /matchmaker/join*/
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        log.info("Join request. name={}",name);
        try {
            Player currentPlayer;
            try {
                currentPlayer = playersRepository.get(name);
            } catch (NoSuchFieldException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            int ratingRange = 30;
            GameSession result = null;
            while (((result = gameSessionsRepository.get(
                    currentPlayer.getRating() - ratingRange,
                    currentPlayer.getRating() + ratingRange)) == null)
                    && (ratingRange < 200)) {
                ratingRange += 10;
            }
            /*Не создается сессия номер 2(всегда игроки добавляются в первую).Пофиксил это
            удалением игры из репозитория. Мне не нравится это решение т.к. это планировалось как база данных всех игр,
            а не как очередь незаполненных.Вопрос нормального перескока на следующий ID открыт. Скорее всего, надо трогать
            GameService.
             */
            if (result == null) {
                result = new GameSession(create(),maxPlayersInSession);
            }
            result.add(currentPlayer);
            if (result.isFull()) {
                start(result.getID());
                gameSessionsRepository.remove(result);
            }
            log.info(gameSessionsRepository.toString());
            return new ResponseEntity<String>(String.valueOf(result.getID()),HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Unable to find a game",HttpStatus.TOO_MANY_REQUESTS);
        }
    }


    /*returns ID of a created game*/
    private long create() throws IOException{
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "playerCount=" + maxPlayersInSession))
                .url(PROTOCOL + HOST + PORT + "/game/create")
                .build();
        Response response = client.newCall(request).execute();
        return  Long.parseLong(response.body().string());
    }

    private void connect(long GameID,String name) throws IOException{
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,
                        "gameId=" + GameID + "&name=" + name))
                .url(PROTOCOL + HOST + PORT + "/game/connect")
                .build();
        client.newCall(request).execute();
    }

    private void start(long GameID) throws IOException{
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "gameId=" + GameID))
                .url(PROTOCOL + HOST + PORT + "/game/start")
                .build();
        client.newCall(request).execute();
    }
}
