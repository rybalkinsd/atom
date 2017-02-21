package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthOk;
import utils.JSONHelper;

import java.io.IOException;

public class PacketAuthOk {
  private int playerID = 0;
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketAuthOk.class);

  @Deprecated
  public PacketAuthOk() {
  }

  public PacketAuthOk(int playerID) {
    this.playerID = playerID;
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toJSON(new CommandAuthOk(playerID));
    log.info("Sending [" + msg + "]");
    session.getRemote().sendString(msg);
  }
}
