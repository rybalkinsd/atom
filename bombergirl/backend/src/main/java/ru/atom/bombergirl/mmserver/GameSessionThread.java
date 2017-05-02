package ru.atom.bombergirl.mmserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.bombergirl.gamemodel.model.Ticker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 02.05.17.
 */
public class GameSessionThread implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    private GameSession session;

    public GameSessionThread(GameSession session) {
        this.session = session;
    }

    public void run() {
        log.info(Thread.currentThread().getName() + " started");
        while (!Thread.currentThread().isInterrupted()) {
            Ticker ticker = new Ticker(session);
            ticker.loop();
        }
    }
}
