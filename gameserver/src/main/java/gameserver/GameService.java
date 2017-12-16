package gameserver;

import boxes.GameCreatorQueue;
import boxes.GameStarterQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("/game")
public class GameService {
    private static final Logger log = LogManager.getLogger(GameService.class);

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam("playerCount") int playerCount) {

        log.info("New connection initialized game session for {} players", playerCount);
        GameCreatorQueue.getInstance().offer(playerCount);

    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void start(@RequestParam("gameId") int gameId) {

        log.info("New connection started game with id {}", gameId);
        GameStarterQueue.getInstance().offer(gameId);

    }
}
