package main.java.zagar.network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandEjectMass;
import main.java.zagar.Game;
import main.java.zagar.util.JSONHelper;

import java.io.IOException;

public class PacketEjectMass {
  @NotNull
  private static final Logger log = LogManager.getLogger(">>>");

  public PacketEjectMass() {
  }

  public void write() throws IOException {
    String msg = JSONHelper.toJSON(new CommandEjectMass());
    log.info("Sending [" + msg + "]");
    Game.socket.session.getRemote().sendString(msg);
  }
}
