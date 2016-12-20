package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * Created by xakep666 on 28.11.16.
 * <p>
 * Replicates session leaderboard to clients
 */
public class LeaderboardReplicator {
    @NotNull
    private static final Logger log = LogManager.getLogger(LeaderboardReplicator.class);
    public void replicate() {
        ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()
                .forEach(gameSession -> {
                    String[] lb = gameSession.getTop(10).entrySet().stream()
                            .map(e -> e.getKey().getUser().getName())
                            .toArray(String[]::new);
                    gameSession.getPlayers().forEach(
                            player -> {
                                Session session = ApplicationContext.instance()
                                        .get(ClientConnections.class)
                                        .getSessionByPlayer(player);
                                if (session == null) return;
                                try {
                                    new PacketLeaderBoard(lb).write(session);
                                } catch (IOException e) {
                                    log.fatal(e.getMessage());
                                }
                            });
                });
    }
}
