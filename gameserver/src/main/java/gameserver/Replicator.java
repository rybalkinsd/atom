
package gameserver;

import boxes.ConnectionPool;
import gameobjects.GameSession;
import message.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Replicator {
    private static final Logger log = LoggerFactory.getLogger(Replicator.class);

    public void writeReplica(GameSession gameSession) {


        String[] playerList = null;
        ConcurrentLinkedQueue<String> connectionPool = new ConcurrentLinkedQueue<String>();
        connectionPool = ConnectionPool.getInstance().getPlayersWithGameId((int) gameSession.getId());

        while (!connectionPool.isEmpty()) {

            WebSocketSession session = ConnectionPool.getInstance().getSession(connectionPool.poll());

            Broker.getInstance().send(ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                    "[" + gameSession.jsonStringWalls() + gameSession.jsonStringBonus() +
                    gameSession.jsonStringBoxes() + gameSession.jsonStringBombs() +
                            gameSession.jsonStringExplosions() + gameSession.jsonBomberGirl()
                            + "], \"gameOver\":" + gameSession.getGameOver());
        }

    }
}