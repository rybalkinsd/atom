package zagar.network.handlers;

import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthFail;
import zagar.util.JSONHelper;
import zagar.Game;
import zagar.util.Reporter;

import java.io.IOException;

public class PacketHandlerAuthFail {
  public PacketHandlerAuthFail(@NotNull String json) {
    CommandAuthFail commandAuthFail;
    try {
      commandAuthFail = (CommandAuthFail) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      ex.printStackTrace();
      return;
    }
    Game.state = Game.GameState.NOT_AUTHORIZED;
    Reporter.reportFail("Token authentication failed", commandAuthFail.getCause());
  }
}
