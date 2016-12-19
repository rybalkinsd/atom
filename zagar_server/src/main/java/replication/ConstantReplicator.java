package replication;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Alex on 27.11.2016.
 */
public class ConstantReplicator implements Replicator {
    @NotNull
    private final static Logger log = LogManager.getLogger(MasterServer.class);

    @Override
    public void replicate() {
        FileInputStream in = null;
        String msg = "";
        try {
            in = new FileInputStream("src/main/resources/replicateinput.txt");
            int content;
            while ((content = in.read()) != -1) {
                msg = msg + (char) content;
            }
            System.out.println(msg);
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }

        CommandReplicate commandReplicate;
        try {
            commandReplicate = JSONHelper.fromJSON(msg, CommandReplicate.class);
        } catch (JSONDeserializationException e) {
            log.error("JSON Deserialization", e);
            return;
        }
        protocol.model.Cell[] cells = new Cell[commandReplicate.getCells().length];
        for (int i = 0; i < commandReplicate.getCells().length; i++) {
            protocol.model.Cell c = commandReplicate.getCells()[i];
            cells[i] = new protocol.model.Cell(c.getCellId(), c.getPlayerId(), c.isVirus(), c.getSize(), c.getX(), c.getY());
        }
        Food[] food = new Food[0];
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {

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

