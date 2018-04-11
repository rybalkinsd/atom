package mm.Service;

import mm.Repo.GameSession;
import mm.Repo.GameSessionsRepository;
import playerdb.PlayersRepository;
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

@Controller
@RequestMapping("/game")
public class GameService {

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    private long currentGameSessionId = 0;

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
        currentGameSessionId++;
        gameSessionsRepository.put(result = new GameSession(currentGameSessionId,playerCount));
        log.info("Created: Game ID{}", result.getId());
        return new ResponseEntity<>(String.valueOf(result.getId()),HttpStatus.OK);
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> start(@RequestParam("gameId") long gameId) {
        GameSession result;
        try {
            result = gameSessionsRepository.get(gameId);
            gameSessionsRepository.remove(result);//questionable
        } catch (NoSuchFieldException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Started: Game ID{}", result.getId());
        return new ResponseEntity<>(String.valueOf(result.getId()),HttpStatus.OK);
    }

    @RequestMapping(
            path = "connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    /* For now we use HTTP. Replace with WebSocket later */
    public ResponseEntity<String> connect(@RequestParam("gameId") long gameId,
                                          @RequestParam("name") String name) {
        GameSession result;
        try {
            result = gameSessionsRepository.get(gameId);
            result.add(playersRepository.get(name));
        } catch (NoSuchFieldException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Connected: Player={} to GameId={}",name, result.getId());
        return new ResponseEntity<>(String.valueOf(result.getId()),HttpStatus.OK);
    }

}
