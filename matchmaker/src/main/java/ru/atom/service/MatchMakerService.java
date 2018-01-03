package ru.atom.service;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.atom.dao.GameDao;
import ru.atom.dao.PlayerDao;
import ru.atom.service.ConnectionQueue;
import ru.atom.model.Game;
import ru.atom.model.Player;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class MatchMakerService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MatchMakerService.class);
    private Thread connectionHandlerThread = new Thread(new ConnectionHandler(this));

    @Value("${REQUIRED_PLAYER_AMOUNT}")
    private volatile int requiredPlayerAmount;

    @Value("${gameServerUrl}")
    private volatile String gameServerUrl;


    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private GameDao gameDao;

    public int getRequiredPlayerAmount() {
        return this.requiredPlayerAmount;
    }

    public String getGameServerUrl() {
        return this.gameServerUrl;
    }

    public MatchMakerService() {
        connectionHandlerThread.start();
    }

    @Transactional
    public Player getPlayerByName(@NotNull String name) {
        return playerDao.getPlayerByName(name);
    }

    public void handleGameSessionClose(long gameId) {
        try {
            gameDao.delete(gameId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public Long handleConnection(String name) {
        Thread myThread = Thread.currentThread();
        Player player = new Player().setName(name);
        Connection connection = new Connection(myThread, player);
        ConnectionQueue.getInstance().offer(connection);
        savePlayer(player);
        try {
            synchronized (myThread) {
                myThread.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        savePlayer(player);

        return player.getGame().getId();
    }

    @Transactional
    public void saveGame(Game game) {
        gameDao.save(game);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerDao.save(player);
    }





}


