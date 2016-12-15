package zagar.network.packets;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import zagar.util.JSONHelper;
import java.io.IOException;

public class PacketMove {
  public float x;
  public float y;

  public PacketMove(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public void write(@NotNull Session s) throws IOException {
    String msg = JSONHelper.toJSON(new CommandMove(x, y));
    s.getRemote().sendString(msg);
  }
}