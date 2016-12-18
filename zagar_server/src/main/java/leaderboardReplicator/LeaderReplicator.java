package leaderboardReplicator;

import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Max on 29.11.2016.
 */
public class LeaderReplicator implements LeaderboardReplicator {
    private static final Logger log = LogManager.getLogger(LeaderReplicator.class);
    @Override
    public void replicate() {
        String SJSON;
        try{
            FileReader FR = new FileReader("Leader.json");
            BufferedReader BFR = new BufferedReader(FR);
            SJSON = BFR.readLine();
        } catch (FileNotFoundException e) {
            log.info("LeaderJSON file not found");
            SJSON = "{ \"Leaders\": }";
        } catch (IOException e) {
            log.info("Cannot read JSON");
            SJSON = "{ \"Leaders\": }";
        }
        log.info(SJSON);
        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(SJSON).getAsJsonObject();
        JsonArray Leaders = mainObject.getAsJsonArray("Leaders");
        String[] S1 = new String[Leaders.size()];
        int i=0;
        for(JsonElement S: Leaders){
            S1[i]= S.getAsString();
            i++;
        }

        for (GameSession gameSession : ApplicationContext.get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.get(ClientConnections.class).getConnections()) {
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
