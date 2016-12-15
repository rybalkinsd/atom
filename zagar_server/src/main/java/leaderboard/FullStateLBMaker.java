package leaderboard;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import mechanics.Mechanics;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandLeaderBoard;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Alex on 08.12.2016.
 */
public class FullStateLBMaker implements LdrBrdMaker {
    @NotNull
    private final static Logger log = LogManager.getLogger(Mechanics.class);

    @Override
    public void makeLB() throws IOException {
        String[] leaderboard = new String[10];
        CommandLeaderBoard commandLeaderBoard;
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            ArrayList<Player> players = new ArrayList<>(gameSession.getPlayers());
            players.sort((o1, o2) -> o1.getScore().compareTo(o2.getScore()));
            for (int i=0; i < 10; i++){
                if (players.size() > i){
                    Player toLB = players.get(i);
                    leaderboard[i] = toLB.getName() + " " + toLB.getScore();
                }
                else {
                    leaderboard[i] = "";
                }
            }
            log.info ("LeaderBoard created:" + leaderboard.toString());
            commandLeaderBoard = new CommandLeaderBoard(leaderboard);
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketLeaderBoard(commandLeaderBoard.getLeaderBoard()).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
