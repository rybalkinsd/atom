package ru.atom.gameserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.atom.gameserver.service.GameRepository;

@RestController
@RequestMapping("game")
public class GameServiceController {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceController.class);

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createGame(@RequestParam("playerCount") int playerCount) {
        logger.info("received /game/create request " + playerCount);
        Long gameId = gameRepository.createGame(playerCount);
        return ResponseEntity.ok().body(gameId.toString());
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void startGame(@RequestParam("gameId") long gameId) {
        logger.info("received /game/start request");
        //gameRepository.getGameById(gameId).start();
    }
}
