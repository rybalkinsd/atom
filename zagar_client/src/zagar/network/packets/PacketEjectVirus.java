package zagar.network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandEjectVirus;
import zagar.Game;
import zagar.util.JSONHelper;

import java.io.IOException;

public class PacketEjectVirus {
  @NotNull
  private static final Logger log = LogManager.getLogger(">>>");

  public PacketEjectVirus() {
  }

  public void write() throws IOException {
    String msg = JSONHelper.toJSON(new CommandEjectVirus());
    log.info("Sending [" + msg + "]");
    Game.socket.session.getRemote().sendString(msg);
  }
}
