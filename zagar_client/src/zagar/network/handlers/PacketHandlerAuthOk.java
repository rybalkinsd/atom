package zagar.network.handlers;

import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthOk;
import zagar.Game;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;

public class PacketHandlerAuthOk {
  public PacketHandlerAuthOk(@NotNull String json) {
    Game.state = Game.GameState.AUTHORIZED;
    CommandAuthOk commandAuthOk;
    try {
      commandAuthOk = JSONHelper.fromJSON(json, CommandAuthOk.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    Game.playerId = commandAuthOk.getPlayerID();
  }
}