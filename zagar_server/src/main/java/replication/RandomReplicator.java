package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.handlers.PacketHandlerMove;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * Created by s on 30.11.16.
 */
public class RandomReplicator implements Replicator {
    private final static Logger log = LogManager.getLogger(RandomReplicator.class);
    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            int SIZE = 5;
            Food[] food = new Food[SIZE];
            Cell[] cells = new Cell[SIZE];
            Random rnd = new Random();
            for (int i = 0; i < SIZE; i++) {
                food[i] = new Food();
                cells[i] = new Cell(rnd.nextInt(100), rnd.nextInt(100), false, (float) (50.0 * rnd.nextFloat()), rnd.nextInt(300), rnd.nextInt(300));
            }
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketReplicate(cells, food).write(connection.getValue());
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}
