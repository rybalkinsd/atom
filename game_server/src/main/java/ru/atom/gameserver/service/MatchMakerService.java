package ru.atom.gameserver.service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MatchMakerService {

    private static final Logger logger = LoggerFactory.getLogger(MatchMakerService.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    @Value("${mmserver}")
    private String mmServer;
    @Value("${mmport}")
    private int port;
    @Value("${mmgameover}")
    private String mmGameOver;
    @Value("${mmdisconnect}")
    private String mmDisconnect;

    public void disconnectionWithPlayer(String login) {
        logger.info("Disconnect player with login: " + login);

        Response response = sendRequest(login, mmDisconnect);
        if (response == null || !response.isSuccessful()) {
            logger.warn("Send Disconnection error!");
        } else {
            response.close();
        }
    }

    public void sendGameOver(String winnerLogin) {
        logger.info("Winner login: " + winnerLogin);

        Response response = sendRequest(winnerLogin, mmGameOver);
        if (response == null || !response.isSuccessful()) {
            logger.warn("Send Game Over error!");
        }
    }

    private Response sendRequest(String login, String urlEnding) {
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,"login=" + login))
                .url(mmServer + ":" + port + urlEnding)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return response;
    }

}
