package replication;

import main.ApplicationContext;
import model.Player;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eugene on 11/25/16.
 */
@TestOnly
public class FakeStateReplicator implements Replicator {
  public static class CellsFoods {
    @NotNull private protocol.model.Cell[] cells;
    @NotNull private protocol.model.Food[] foods;

    public protocol.model.@NotNull Cell[] getCells() {
      return cells;
    }

    public void setCells(protocol.model.@NotNull Cell[] cells) {
      this.cells = cells;
    }

    public protocol.model.@NotNull Food[] getFoods() {
      return foods;
    }

    public void setFoods(protocol.model.@NotNull Food[] foods) {
      this.foods = foods;
    }
  }

  @Override
  public void replicate() {
    String src;
    try (FileInputStream stream = new FileInputStream("src/test/resources/fake_replica.json")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      src = reader.lines().collect(Collectors.joining());
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    @NotNull CellsFoods cellsFoods;
    try {
      cellsFoods = JSONHelper.fromJSON(src, CellsFoods.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }

    for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
      try {
        new PacketReplicate(cellsFoods.getCells(),cellsFoods.getFoods()).write(connection.getValue());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
