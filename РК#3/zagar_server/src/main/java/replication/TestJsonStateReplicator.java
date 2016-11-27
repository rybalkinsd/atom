package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONDeserializationException;
import utils.JSONHelper;
import utils.PropertiesReader;

import java.io.*;
import java.util.Map;

/**
 * Created by venik on 27.11.16.
 */
public class TestJsonStateReplicator implements Replicator {

    @Override
    public void replicate() {
        String replicationJson = new String();
        try (InputStream in = new FileInputStream(new File("src/main/resources/tmp/replJson.json"));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            replicationJson = reader.readLine();
        }catch (Exception e){}

        String leaderJson = new String();
        try (InputStream in = new FileInputStream(new File(new PropertiesReader("src/main/resources/config.properties").getStringProperty("leaderboard")));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            leaderJson = reader.readLine();
        }catch (Exception e){}

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketReplicate(replicationJson).write(connection.getValue());
                        String[] t = JSONHelper.fromJSON(leaderJson,String[].class);
                        new PacketLeaderBoard(JSONHelper.fromJSON(leaderJson,String[].class)).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONDeserializationException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

}
