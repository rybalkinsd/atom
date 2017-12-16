package gameserver;

import gamemechanic.GameMechanics;
import gamemechanic.InputQueue;
import gamemechanic.Ticker;
import gamesession.GameSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameServer implements Runnable {
    private static final Logger log = LogManager.getLogger(GameServer.class);

    @Override
    public void run() {
        log.info("GS started");
        GameSession.createMap();
        Ticker ticker = new Ticker();
        ticker.gameLoop();
    }

}
