package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.*;
import java.util.Map;

/**
 * Created by venik on 27.11.16.
 */
public class TestJsonStateReplicator implements Replicator {

    @Override
    public void replicate() {
        String json = new String();
        try (InputStream in = new FileInputStream(new File("src/main/resources/tmp/file.json"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            json = reader.readLine();
        }catch (Exception e){}

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketReplicate(json).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}
