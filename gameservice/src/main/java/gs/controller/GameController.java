package gs.controller;

import gs.storage.SessionStorage;
import gs.ticker.Ticker;
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
import gs.service.GameService;

@Controller
@RequestMapping("game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    SessionStorage sessionStorage;

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
        Ticker ticker = new Ticker(sessionStorage.getSessionById(gameId));
        sessionStorage.putTicker(ticker, sessionStorage.getSessionById(gameId));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
