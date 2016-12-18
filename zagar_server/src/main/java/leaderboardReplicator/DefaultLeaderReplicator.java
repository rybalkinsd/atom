package leaderboardReplicator;

import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Max on 17.12.2016.
 */
public class DefaultLeaderReplicator implements LeaderboardReplicator {
    private static final Logger log = LogManager.getLogger(DefaultLeaderReplicator.class);
    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.get(ClientConnections.class).getConnections()) {
                String[] S1 = new String[gameSession.getPlayers().size()];
                int i=0;
                for(Player p:gameSession.getPlayers()){
                    S1[i] = p.getName() + " : " + p.getPts();
                    i++;
                }
                if (gameSession.getPlayers().contains(connection.getKey()) && connection.getValue().isOpen()) {
                    try {
                        new PacketLeaderBoard(S1).write(connection.getValue());
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
