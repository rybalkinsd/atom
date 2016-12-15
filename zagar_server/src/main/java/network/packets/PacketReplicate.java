package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketReplicate {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketReplicate.class);
  @NotNull
  private final Cell[] cells;
  @NotNull
  private final Food[] food;

  public PacketReplicate(@NotNull Cell[] cells, @NotNull Food[] food) {

    this.cells = cells;
    this.food = food;
  }

  @NotNull
  public Food[] getFood(){
    return food;
  }

  @NotNull
  public Cell[] getCells() {
    return cells;
  }

  public PacketReplicate(String json) {
    PacketReplicate packetReplicate = new PacketReplicate(new Cell[0],new Food[0]);
    try {
      packetReplicate = JSONHelper.fromJSON(json,PacketReplicate.class);
    } catch (JSONDeserializationException e) {

      e.printStackTrace();
    }
    this.cells = packetReplicate.getCells();
    this.food = packetReplicate.getFood();
  }

  public void write(@NotNull Session session) {
    try {
      String msg = JSONHelper.toSerial(new CommandReplicate(food, cells));
      session.getRemote().sendString(msg);
    } catch (Exception ex)
    {
      log.error("Failed to send",ex);
    }
  }
}
