package zagar.network.packets;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import zagar.util.JSONHelper;
import zagar.Game;

public class PacketAuth {
  @NotNull
  private static final Logger log = LogManager.getLogger(">>>");
  @NotNull
  private final String login;
  @NotNull
  private final String token;

  public PacketAuth(@NotNull String login, @NotNull String token) {
    this.login = login;
    this.token = token;
  }

  public void write() throws IOException {
    String msg = JSONHelper.toJSON(new CommandAuth(login, token));
    log.info("Sending [" + msg + "]");
    Game.socket.session.getRemote().sendString(msg);
  }
}
