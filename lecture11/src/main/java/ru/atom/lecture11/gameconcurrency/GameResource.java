package ru.atom.lecture11.gameconcurrency;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Broken game server implementation
 * TODO fix concurrency issues
 */
@Controller
@RequestMapping("games")
public class GameResource {
    private Map<String, GameSession> gameId2game = new HashMap<>();
    private Map<String, String> player2gameId = new HashMap<>();

    /**
     * curl -XPOST localhost:8080/games/connect -d "name=sasha&gameId=GAME_ID"
     */
    @RequestMapping(
            path = "connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> connect(@RequestParam("name") String name, @RequestParam("gameId") String gameId) {
        if (name == null) {
            return ResponseEntity.badRequest().body("Player name not provided");
        }
        if (gameId == null) {
            return ResponseEntity.badRequest().body("Player name not provided");
        }
        player2gameId.put(name, gameId);
        return ResponseEntity.ok("Successfully created user [" + name + "]\n");
    }

    /**
     * curl -XPOST localhost:8080/games/create -d "playerCount=4"
     */
    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> create(@RequestParam("playerCount") Integer playerCount) {
        if (playerCount == null) {
            return ResponseEntity.badRequest().body("playerCount not provided");
        }
        UUID uuid = UUID.randomUUID();
        GameSession gameSession = new GameSession(uuid.toString());
        gameId2game.put(uuid.toString(), gameSession);
        return ResponseEntity.ok("Successfully created " + gameSession + "\n");
    }

    /**
     * curl -XPOST localhost:8080/games/start -d "gameId=GAME_ID"
     */
    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> start(@RequestParam("gameId") String gameId) {
        if (gameId == null) {
            return ResponseEntity.badRequest().body("gameId not provided");
        }
        GameSession gameSession = gameId2game.get(gameId);
        if (gameSession == null) {
            return ResponseEntity.badRequest().body("No game with gameId " + gameId);
        }
        gameSession.start();
        return ResponseEntity.ok("Successfully started " + gameSession + "\n");
    }

    /**
     * curl localhost:8080/games/stat
     */
    @RequestMapping(
            path = "stat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStat() {
        return ResponseEntity.ok(this + "\n");
    }

    @Override
    public String toString() {
        return "Game server statistics:" +
                "\ngameId2game=" + gameId2game.values() +
                "\nplayer2gameId=" + player2gameId;
    }
}
