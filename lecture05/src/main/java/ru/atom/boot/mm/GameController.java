package ru.atom.boot.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atom.thread.mm.GameRepository;

/**
 * Created by sergey on 3/15/17.
 */

@Controller
@RequestMapping("/game")
public class GameController {
    private static final Logger log = LogManager.getLogger(GameController.class);

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
        return GameRepository.getAll().toString();
    }
}
