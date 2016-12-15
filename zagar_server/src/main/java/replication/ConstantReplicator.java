package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
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
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        CommandReplicate commandReplicate;
        try {
            commandReplicate = JSONHelper.fromJSON(msg, CommandReplicate.class);
        } catch (JSONDeserializationException e) {
            e.printStackTrace();
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
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

