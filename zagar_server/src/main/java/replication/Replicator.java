package replication;

import leaderboard.LeaderboardState;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.eclipse.jetty.websocket.api.Session;

import java.io.*;
import java.util.Map;

/**
 * @author Alpi
 * @since 31.10.16
 */
public interface Replicator {
  void replicate();
  void sendLeaderboard();
}
