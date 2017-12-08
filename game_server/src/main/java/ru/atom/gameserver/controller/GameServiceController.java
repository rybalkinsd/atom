package ru.atom.gameserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.atom.gameserver.service.GameRepository;

@RestController
@RequestMapping("game")
public class GameServiceController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public ResponseEntity<String> createGame(@RequestParam("playersNum") int playersNum) {
        Long gameId = gameRepository.createGame(playersNum);
        return ResponseEntity.ok().body(gameId.toString());
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(value = HttpStatus.OK)
    public void startGame(@RequestParam("gameId") long gameId) {
        gameRepository.getGameById(gameId).start();
    }
}
