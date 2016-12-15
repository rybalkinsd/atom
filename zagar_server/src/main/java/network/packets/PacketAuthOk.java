package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthOk;
import utils.JSONHelper;

import java.io.IOException;

public class PacketAuthOk {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketAuthOk.class);
  int id;
  public PacketAuthOk(int id) {
    this.id = id;
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toSerial(new CommandAuthOk(id));
    log.info("Sending [" + msg + "]");
    try {
      session.getRemote().sendString(msg);
    } catch (WebSocketException ex) {
      log.error("Failed to send", ex);
    }
  }
}
