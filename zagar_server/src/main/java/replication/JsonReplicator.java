package replication;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

/**
 * Created by s on 29.11.16.
 */
public class JsonReplicator implements Replicator{
    private final static Logger log = LogManager.getLogger(Replicator.class);
    @Override
    public void replicate() {
        String msg="";
        try {
            StringBuilder sb=new StringBuilder("");
            Files.lines(Paths.get("src/main/resources/replica.json"), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
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
