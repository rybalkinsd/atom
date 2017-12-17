package ru.atom.matchmaker.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.atom.matchmaker.model.Player;
import ru.atom.matchmaker.service.DatabaseService;
import ru.atom.matchmaker.service.GameService;
import ru.atom.matchmaker.utils.MatchBuilder;

/**
 * Created by Alexandr on 25.11.2017.
 */
@RestController
@RequestMapping("matchmaker")
public class MatchmakerComponent {
    private static final Logger logger = LoggerFactory.getLogger(MatchmakerComponent.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private DatabaseService databaseService;

    @Value("${maxplayerscount}")
    private int maxPlayersCount;

    private MatchBuilder currentBuilder;

    @RequestMapping(
            path = "signin",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> signin(@RequestParam("login") String login,
                                         @RequestParam("password") String password) {
        logger.info("signin request has been received");
        Player player = databaseService.login(login, password);
        if (player != null) {
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.badRequest().body("bad login or password");
        }
    }

    @RequestMapping(
            path = "signup",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> signup(@RequestParam("login") String login,
                                         @RequestParam("password") String password) {
        logger.info("signup request has been received");
        if (!databaseService.checkSignupLogin(login)) {
            databaseService.signUp(login, password);
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.badRequest().body("login is used");
        }
    }

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> join(@RequestParam("login") String login, @RequestParam("password") String password) {
        logger.info("join request has been received");
        Player player = databaseService.login(login, password);
        if (player != null) {
            long gameId = processJoinRequest(login);
            return ResponseEntity.ok().body(String.valueOf(gameId));
        } else {
            return ResponseEntity.badRequest().body("bad login or password");
        }
    }

    @RequestMapping(
            path = "signout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void signout(@RequestParam("login") String login) {
        logger.info("signout request has been received");
        Player player = databaseService.getPlayer(login);
        if (player != null) {
            databaseService.logout(player);
        }
    }

    private long processJoinRequest(String login) {
        long gameId;
        if (currentBuilder == null) {
            gameId = gameService.create(maxPlayersCount);
            currentBuilder = new MatchBuilder(maxPlayersCount, gameId);
            logger.info("game has been created with id=" + gameId);
        } else {
            gameId = currentBuilder.getGameId();
        }
        currentBuilder.putLogin(login);
        gameService.connect(login, currentBuilder.getGameId());
        if (currentBuilder.isReady()) {
            gameService.start(currentBuilder.getGameId());
            currentBuilder = null;
        }
        return gameId;
    }

    @RequestMapping(
            path = "top",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> top() {
        logger.info("get top players");
        return ResponseEntity.ok().body(databaseService.getTop());
    }

    @RequestMapping(
            path = "onlinelist",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> onlinelist() {
        logger.info("get online players");
        return ResponseEntity.ok().body(databaseService.getOnline());
    }

    @RequestMapping(
            path = "gameover",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> gameover(@RequestParam("login") String winner) {
        logger.info("gameover request has been received");
        if (!winner.isEmpty()) {
            databaseService.incrementStatistic(winner);
        }
        return ResponseEntity.ok().body("ok");
    }

}
