package leaderboard;

import java.io.*;
import java.util.Map;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandLeaderBoard;
import protocol.CommandReplicate;
import utils.JSONDeserializationException;
import utils.JSONHelper;

/**
 * Created by pashe on 28-Nov-16.
 */
public class ConstantLBMaker implements LdrBrdMaker {
    @NotNull
    private final static Logger log = LogManager.getLogger(MasterServer.class);


    @Override
    public void makeLB() throws IOException {
        String[] leaderboard = new String[10];
        FileInputStream in = null;
        String msg = "";
        try {
            in = new FileInputStream("src/main/resources/leaderboardinput.txt");
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
        CommandLeaderBoard commandLeaderBoard;
        try {
            commandLeaderBoard = JSONHelper.fromJSON(msg,CommandLeaderBoard.class);
        } catch (JSONDeserializationException e) {
            log.error(e);
            return;
        }

        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketLeaderBoard(commandLeaderBoard.getLeaderBoard()).write(connection.getValue());
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}
