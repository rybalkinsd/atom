package ru.atom.boot.mm;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;

import ru.atom.thread.mm.ConnectionQueue;
import ru.atom.thread.mm.GameId;
import ru.atom.thread.mm.GameIdQueue;
import ru.atom.thread.mm.GameSession;


import java.util.concurrent.TimeUnit;


@Controller
@CrossOrigin
@RequestMapping("/matchmaker")
public class MatchMakerController {
    private static final Logger log = LogManager.getLogger(MatchMakerController.class);


    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        log.info("New connection  name=" + name);
        ConnectionQueue.getInstance().offer(new GameSession(0, name));
        while (GameIdQueue.getInstance().isEmpty()) {
        }
        try {
            return new ResponseEntity<>(
                    GameIdQueue.getInstance().peek().toString(),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    "-1",
                    HttpStatus.OK);

        }

    }

    @RequestMapping(
            path = "gameId",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void connect(@RequestParam("gameId") long id) {

        log.info("New gameId id=" + id);
        GameIdQueue.getInstance().offer(new GameId(id));
        try {
            MatchMakerClient.toFrontEnd(id);
            log.info("Good request to front-end");
        } catch (Exception e) {
            log.info("Bad request to front-end");
        }

    }


    @RequestMapping(
            path = "gameId",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> gameId() {
        try {
            return new ResponseEntity<>(
                    GameIdQueue.getInstance().peek().toString(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "-1",
                    HttpStatus.OK);

        }
    }


    //Тестирование
    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void chek0(@RequestParam("playerCount") long id) {

        log.info("Create: " + id);
    }

    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void chek1(@RequestParam("gameId") long id) {

        log.info("Start: " + id);
    }


}
