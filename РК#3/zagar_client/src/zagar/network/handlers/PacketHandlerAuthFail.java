package zagar.network.handlers;

import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthFail;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;
import zagar.Game;
import zagar.util.Reporter;

public class PacketHandlerAuthFail {
  public PacketHandlerAuthFail(@NotNull String json) {
    CommandAuthFail commandAuthFail;
    try {
      commandAuthFail = JSONHelper.fromJSON(json, CommandAuthFail.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    Game.state = Game.GameState.NOT_AUTHORIZED;
    Reporter.reportFail("Token authentication failed", commandAuthFail.getCause());
  }
}
