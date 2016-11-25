package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthOk;
import protocol.CommandSplit;
import utils.JSONHelper;

import java.io.IOException;

public class PacketAuthOk {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketAuthOk.class);
  public PacketAuthOk() {
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toJSON(new CommandAuthOk());
    log.info("Sending [" + msg + "]");
    session.getRemote().sendString(msg);
  }
}
