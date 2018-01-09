package ru.atom.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.service.ConnectionQueue;
import ru.atom.model.Player;
import ru.atom.service.MatchMakerService;

import javax.validation.constraints.NotNull;
import java.util.List;


@Controller
@RequestMapping("matchmaker")
public class MatchMakerController {

    @Autowired
    private MatchMakerService mmService;

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        Player alreadyInGame = mmService.getPlayerByName(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        if (name == null || name == "") {
            return ResponseEntity.badRequest().headers(headers)
                    .body("Enter your name");
        }
        if (alreadyInGame != null) {
            return ResponseEntity.badRequest().headers(headers)
                    .body("Already in game");
        }

        return ResponseEntity.ok().headers(headers).body(mmService.handleConnection(name).toString());
    }


    @RequestMapping(
            path = "closegs",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> gameSessionClose(@RequestBody String gameId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        mmService.handleGameSessionClose(Long.parseLong(gameId));

        return ResponseEntity.ok().headers(headers).body(gameId);
    }


}
