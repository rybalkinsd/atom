package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
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

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toJSON(new CommandReplicate(food, cells));
    log.info("Sending [" + msg + "]");
    session.getRemote().sendString(msg);
  }
}
