package bomber.matchmaker.thread;


import bomber.matchmaker.controller.MmController;
import bomber.matchmaker.request.MmRequests;
import bomber.matchmaker.service.BomberService;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

import static bomber.matchmaker.controller.MmController.MAX_PLAYER_IN_GAME;
import static java.util.concurrent.TimeUnit.SECONDS;

public class StartThread extends Thread {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StartThread.class);
    private volatile Integer gameId;
    private static final int TIMEOUT = 15;
    private static final int MAX_TIMEOUTS = 3;
    private volatile boolean isStarted;
    private final BomberService bomberService;


    public StartThread(Integer gameId, BomberService bomberService) {
        super("StartThread_gameId=" + gameId);
        this.gameId = gameId;
        this.bomberService = bomberService;
        isStarted = false;

    }

    @Override
    public void run() {
        int tryCounter = 0;
        int playersConnected = 0;
        while (tryCounter <= MAX_TIMEOUTS + 1
                && !isStarted) {
            try {
                synchronized (gameId) {
                    playersConnected = Integer.parseInt(MmRequests.checkStatus(gameId).body().string());
                }

                if (playersConnected == MAX_PLAYER_IN_GAME) {
                    bomberService.addToDb(gameId, new Date());
                    log.info("Sending a request to start the game with {} out of {} players in it, gameID = {}",
                            playersConnected, MAX_PLAYER_IN_GAME, gameId);
                    MmRequests.start(this.gameId);
                    isStarted = true;
                } else {
                    synchronized (gameId) {
                        if (tryCounter == MAX_TIMEOUTS + 1)
                            break;
                        log.info("Timeout for {} SECONDS, waiting for players to CONNECT. {} TIMEOUTS left. " +
                                        "{} out of {} players connected",
                                TIMEOUT, MAX_TIMEOUTS - tryCounter, playersConnected, MAX_PLAYER_IN_GAME);
                        sleep(SECONDS.toMillis(TIMEOUT));
                    }
                }
            } catch (IOException e) {
                log.info("failed to execute the start game request");
            } catch (InterruptedException e) {
                log.error("Sleep of thread={} interrupted", currentThread());
            }
            tryCounter++;
        }
        if (!isStarted) {
            if (playersConnected == 0 || playersConnected == 1) {
                log.info("failed to start the game");
            } else {
                bomberService.addToDb(gameId, new Date());
                log.info("Sending a request to start the game with {} out of {} players in it, gameID = {}",
                        playersConnected, MAX_PLAYER_IN_GAME, gameId);
                try {
                    MmRequests.start(this.gameId);
                } catch (IOException e) {
                    log.info("failed to execute the start game request");
                }
                isStarted = true;
            }
        }

        MmController.clear();
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

}