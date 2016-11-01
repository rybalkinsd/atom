package zagar.network.packets;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import zagar.util.JSONHelper;

public class PacketMove {
  @NotNull
  private static final Logger log = LogManager.getLogger(">>>");

  public float x;
  public float y;

  public PacketMove(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void write(@NotNull Session s) throws IOException {
    String msg = JSONHelper.toJSON(new CommandMove(x, y));
    //log.info("Sending [" + msg + "]");
    //s.getRemote().sendString(msg);TODO
  }
}
