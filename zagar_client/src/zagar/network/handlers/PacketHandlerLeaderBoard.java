package zagar.network.handlers;

import org.jetbrains.annotations.NotNull;
import protocol.CommandLeaderBoard;
import zagar.Game;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;

public class PacketHandlerLeaderBoard {
  public PacketHandlerLeaderBoard(@NotNull String json) {
    CommandLeaderBoard commandLeaderBoard;
    try {
      commandLeaderBoard = JSONHelper.fromJSON(json, CommandLeaderBoard.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    Game.leaderBoard = commandLeaderBoard.getLeaderBoard();
  }
}