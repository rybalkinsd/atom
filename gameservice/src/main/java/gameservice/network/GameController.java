package gameservice.network;

import gameservice.Sessions;
import gameservice.gamemechanics.GameMechanics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import gameservice.service.GameService;

@Controller
@RequestMapping("game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    Sessions sessions;

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> create(@RequestParam("playerCount") int playerCount) {
        long gameId = gameService.create(playerCount);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>(gameId, headers, HttpStatus.OK);
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> start(@RequestParam("gameId") long gameId) {
        GameMechanics gameMechanics = new GameMechanics(Sessions.getSessionById(gameId));
        Sessions.putTickables(gameMechanics, Sessions.getSessionById(gameId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
