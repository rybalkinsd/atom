package ru.atom.matchmaker.service;

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

/**
 * Created by Alexandr on 25.11.2017.
 */
@Service
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private static final OkHttpClient client = new OkHttpClient();

    @Value("${url}")
    private String url;
    @Value("${create}")
    private String create;
    @Value("${start}")
    private String start;

    public long create(int playerCount) {
        logger.info("create new game for " + playerCount + "th players");
        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "playerCount=" + playerCount))
                .url(url + create)
                .build();
        logger.info(url + create);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                logger.info("success " + responseString);
                return Long.valueOf(responseString);
            } else {
                logger.info("smth wrong");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    public void connect(String playerLogin, long gameId) {

    }

    public void start(long gameId) {
        logger.info("start game with id = " + gameId);
        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "gameId=" + gameId))
                .url(url + start)
                .build();
        logger.info(url + start);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("start game successful");
            } else {
                logger.info("smth wrong");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
