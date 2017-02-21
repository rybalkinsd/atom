package main.java.zagar.network.handlers;

import main.java.zagar.Game;
import main.java.zagar.util.JSONDeserializationException;
import main.java.zagar.util.JSONHelper;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandLeaderBoard;

public class PacketHandlerLeaderBoard implements PacketHandler {
  public void handle(@NotNull String json) {
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
