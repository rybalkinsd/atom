import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/game")
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    private long currentGameSessionID = 0;

    @Autowired
    private GameSessionsRepository gameSessionsRepository;

    @Autowired
    private PlayersRepository playersRepository;

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> create(@RequestParam("playerCount") int playerCount) {
        GameSession result;
        currentGameSessionID++;
        gameSessionsRepository.put(result = new GameSession(currentGameSessionID,playerCount));
        log.info("Game session ID{} created", result.getID());
        return new ResponseEntity<>(String.valueOf(result.getID()),HttpStatus.OK);
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> start(@RequestParam("gameId") long gameID ) {
        GameSession result;
        try {
            result = gameSessionsRepository.get(gameID);
        } catch (NoSuchFieldException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Game session ID{} started", result.getID());
        return new ResponseEntity<>(String.valueOf(result.getID()),HttpStatus.OK);
    }

    @RequestMapping(
            path = "connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    /* For now we use HTTP. Replace with WebSocket later */
    public ResponseEntity<String> connect(@RequestParam("gameId") long gameID,
                                          @RequestParam("name") String name) {
        GameSession result;
        try {
            result = gameSessionsRepository.get(gameID);
            result.add(playersRepository.get(name));
        } catch (NoSuchFieldException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Game session ID{} started", result.getID());
        return new ResponseEntity<>(String.valueOf(result.getID()),HttpStatus.OK);
    }

}
