package replication;

import main.ApplicationContext;
import matchmaker.IMatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.TestOnly;
import utils.JSONDeserializationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static utils.JSONHelper.fromJSON;

/**
 * Created by eugene on 11/25/16.
 */
@TestOnly
public class FakeLeaderBoardReplicator implements Replicator {

  String readFile(String fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    try {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append("\n");
        line = br.readLine();
      }
      return sb.toString();
    } finally {
      br.close();
    }
  }

  @Override
  public void replicate() {
    //TODO
    String leadersString = "{}";
    Player[] leaders;
    List<Player> leadersList = null;

    try {
      leadersString = readFile("src/test/resources/fake_leaders.json");
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      leaders = fromJSON(leadersString, Player[].class);
      leadersList = new ArrayList<Player>(Arrays.asList(leaders));
      System.out.println(leaders[4].getName());
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
    }

    String[] leadersArray = leadersList.stream()
            .map(each -> each.getName())
            .toArray(String[]::new);

    for (GameSession gameSession : ApplicationContext.instance().get(IMatchMaker.class).getActiveGameSessions()) {
      for (Map.Entry<Player, Session> connection : ApplicationContext.instance()
              .get(ClientConnections.class).getConnections()) {
        if (gameSession.getPlayers().contains(connection.getKey())) {
          try {
            new PacketLeaderBoard(leadersArray).write(connection.getValue());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
