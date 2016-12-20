package zagar.network.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthFail;
import zagar.GameThread;
import zagar.util.JSONHelper;
import zagar.Game;
import zagar.util.Reporter;

import java.io.IOException;

public class PacketHandlerAuthFail {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketHandlerAuthFail.class);
  public PacketHandlerAuthFail(@NotNull String json) {
    CommandAuthFail commandAuthFail;
    try {
      commandAuthFail = (CommandAuthFail) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("failed to deserialize auth fail command",ex);
      return;
    }
    Game.state = Game.GameState.NOT_AUTHORIZED;
    Reporter.reportFail("Token authentication failed", commandAuthFail.getCause());
  }
}
