package ru.atom.mm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atom.mm.service.GameRepository;

/**
 * Created by sergey on 3/15/17.
 */

@Controller
@RequestMapping("/game")
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);


    @Autowired
    private GameRepository gameRepository;

    /**
     * curl test
     *
     * curl -i localhost:8080/game/list
     */
    @RequestMapping(
            path = "list",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String list() {
        log.info("Games list request");
        return gameRepository.getAll().toString();
    }
}
