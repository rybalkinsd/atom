package leaderboard;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by s on 30.11.16.
 */
public class JsonLeaderBoard implements Leaderboard {
    private final static @NotNull Logger log = LogManager.getLogger(RandomLeaderBoard.class);

    @Override
    public void sendleaders() {
        String msg="";
        try {
            StringBuilder sb=new StringBuilder("");
            Files.lines(Paths.get("src/main/resources/leaders.json"), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
            msg=sb.toString();
        } catch (IOException e) {
            log.error(e);
        }

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        connection.getValue().getRemote().sendString(msg);
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}
