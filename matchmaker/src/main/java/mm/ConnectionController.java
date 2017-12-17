package mm;

import com.fasterxml.jackson.databind.ObjectMapper;
import mm.dao.PlayerDao;
import mm.dao.JsonHistory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static mm.dao.ReturnValue.TRUE;

@Controller
@CrossOrigin
@RequestMapping("matchmaker")
public class ConnectionController {
    private static final Logger log = LogManager.getLogger(ConnectionController.class);

    @Autowired
    static Matchmaker matchMaker = new Matchmaker();
    private static final PlayerDao playerDao = new PlayerDao();

    private static final HttpHeaders headers = new HttpHeaders();

    static {
        Thread matchMakerThread = new Thread(matchMaker);
        matchMakerThread.start();
        playerDao.reset();
    }

    /**
     * curl test
     * <p>curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" localhost:8080/matchmaker/join
     * -d "name=bomberman"</p>
     */

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        if (!(playerDao.playerExists(name) == TRUE)) {
            //TODO: change HttpStatus
            return new ResponseEntity<>("-", headers, HttpStatus.OK);
        }
        log.info(name + " joins");
        if (matchMaker.join(name)) {
            log.info(name + " added to queue");
        } else {
            log.error(name + " cannot be added to queue");
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long gameId = null;
        while (gameId == null) {
            gameId = matchMaker.inGamePlayers.get(name);
            if (gameId == null)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
        }
        log.info("gameId = " + gameId.toString() + " sent to " + name);
        return new ResponseEntity<>(gameId.toString(), headers, HttpStatus.OK);
    }

    /**
     * curl test
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" localhost:8080/matchmaker/login
     * -d "login=test&password=qwer"
     *</p>
     */

    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
        )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        switch (playerDao.login(login, password)) {
            case TRUE:
                log.info("correct password for player " + login);
                return new ResponseEntity<>("+", headers, HttpStatus.OK);

            case FALSE:
                log.info("incorrect password for player " + login);
                return new ResponseEntity<>("-", headers, HttpStatus.OK);

            case ERROR:
                log.error("Failed to login player" + login);
                return new ResponseEntity<>("", headers, HttpStatus.INTERNAL_SERVER_ERROR);

            default:
                break;
        }
        return null;
    }

    /**
     * curl test
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" localhost:8080/matchmaker/register
     * -d "login=test&password=qwer"
     *</p>
     */

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
        )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        if (playerDao.playerExists(login) == TRUE) {
            log.error("Player " + login + " already exists");
            return new ResponseEntity<>("-", headers, HttpStatus.OK);
        } else if (playerDao.register(login, password) == TRUE) {
            log.info("Player " + login + " registered with password " + password);
            return new ResponseEntity<>("+", headers, HttpStatus.OK);
        } else {
            log.error("Failed to register player" + login);
            return new ResponseEntity<>("", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * curl test
     * <p>curl -i localhost:8080/matchmaker/</p>
     */
    @RequestMapping(
            path = "history",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> list(@RequestParam("login") String login) {
        log.info("Games list request");
        ArrayList<Map<String, Integer>> gameHistory = playerDao.getPlayerHistory(login);

        ObjectMapper mapper = new ObjectMapper();
        String jsonGameHistory = null;
        try {
            jsonGameHistory = mapper.writeValueAsString(gameHistory);
        } catch (Exception ignored) {

        }
        if (jsonGameHistory != null)
            System.out.println(jsonGameHistory);
        return new ResponseEntity<>(jsonGameHistory, headers, HttpStatus.OK);
    }

    @RequestMapping(
            path = "score",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> score(@RequestParam("json") String jsonScore) {
        log.info("Score request with data\n" + jsonScore);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonHistory historyEntry = mapper.readValue(jsonScore, JsonHistory.class);
            playerDao.addToHistory(historyEntry);
        } catch (IOException e) {
            log.error("error reading json history string\n" + e);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        }
        log.info("history added to DB");
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(
            path = "kick",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> kick(@RequestParam("login") String login) {
        log.info("player " + login + " kick request");
        log.info(matchMaker.inGamePlayers);
        if (matchMaker.inGamePlayers.containsKey(login)) {
            log.info(login + " kicked");
            matchMaker.inGamePlayers.remove(login);
        } else
            log.info(login + " if not in the game");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
