package ru.atom.network.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.atom.service.GameServerService;

@Controller
@RequestMapping("game")
public class GameServerController {

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> create(@RequestParam("playerCount") String playerCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        Long gameId = GameServerService.createGameSession(Short.parseShort(playerCount));

        return ResponseEntity.ok()
                .headers(headers)
                .body(gameId.toString());
    }

}
