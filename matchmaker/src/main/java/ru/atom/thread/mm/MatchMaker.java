package ru.atom.thread.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.bd.Gamesession;
import ru.atom.bd.UserDao;
import ru.atom.boot.mm.MatchMakerClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MatchMaker implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);


    @Override
    public void run() {
        log.info("Started");
        boolean create = false;
        UserDao userDao = new UserDao();
        List<GameSession> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        MatchMakerMonitoring monitor = new MatchMakerMonitoring();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ConnectionQueue.getInstance().poll(6000, TimeUnit.SECONDS)
                );
                monitor.incrementQueue(1);
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
                if (create) {
                    try {
                        MatchMakerClient.start(GameIdQueue.getInstance().poll().getGameId());
                        create = false;
                        monitor.decrimentQueue(1);
                    } catch (Exception e2) {
                        log.warn("Bad request to GameServer in start request");
                    }
                    log.info("Session created");
                    candidates.clear();
                }
            }


            //Создание сессии

            if (candidates.size() == 1) {
                try {
                    MatchMakerClient.create(GameSession.PLAYERS_IN_GAME);
                    create = true;
                } catch (Exception e) {
                    log.warn("Bad request to GameServer in create request");
                }
            }

            //Старт сессии

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                try {
                    Gamesession newUser = new Gamesession(GameIdQueue.getInstance().peek().getGameId(),
                            candidates.get(0).toString(), candidates.get(1).toString());
                    userDao.insert(newUser);

                    MatchMakerClient.start(GameIdQueue.getInstance().poll().getGameId());
                    create = false;
                    monitor.decrimentQueue(2);
                } catch (Exception e) {
                    log.warn("Bad request to GameServer in start request");
                    GameIdQueue.getInstance().poll();

                }
                log.info("Session created");
                candidates.clear();
            }

        }
    }
}

