package ru.atom.gameserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatchMakerService {

    private static final Logger logger = LoggerFactory.getLogger(MatchMakerService.class);
    private static final String gameOverFormat = "gameId=%d&winner=%s";

    private RestTemplate mmClient = new RestTemplate();
    private HttpHeaders headers;
    @Value("matchmakerserver")
    private String mmServer;
    @Value("gameover")
    private String mmGameOver;

    public MatchMakerService() {
        headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-urlencoded");
    }

    public void sendGameOver(long gameId, String winnerLogin) {
        HttpEntity<String> request = new HttpEntity<>(String.format(gameOverFormat, gameId, winnerLogin), headers);
        ResponseEntity<String> response =
                mmClient.exchange(mmServer + mmGameOver, HttpMethod.POST, request, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            logger.warn("game over response status is not OK!");
        }
    }


}
