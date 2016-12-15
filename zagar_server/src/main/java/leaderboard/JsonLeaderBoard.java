package leaderboard;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by s on 30.11.16.
 */
public class JsonLeaderBoard implements Leaderboard {
    @Override
    public void Sendleaders() {
        String msg="";
        try {
            StringBuilder sb=new StringBuilder("");
            Files.lines(Paths.get("src/main/resources/leaders.json"), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
            msg=sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        connection.getValue().getRemote().sendString(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
