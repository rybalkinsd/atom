package zagar.network.packets;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandSplit;
import zagar.util.JSONHelper;
import zagar.Game;

public class PacketSplit {
  @NotNull
  private static final Logger log = LogManager.getLogger(">>>");

  private final float dx;
  private final float dy;

  public PacketSplit(float dx, float dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public void write() throws IOException {
    String msg = JSONHelper.toSerial(new CommandSplit(dx,dy));
    log.info("Sending [" + msg + "]");
    Game.socket.session.getRemote().sendString(msg);
  }
}
