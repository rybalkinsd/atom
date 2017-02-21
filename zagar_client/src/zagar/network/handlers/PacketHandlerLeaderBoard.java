package zagar.network.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandLeaderBoard;
import zagar.util.JSONHelper;
import zagar.Game;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PacketHandlerLeaderBoard {
  private static final Logger log = LogManager.getLogger(PacketHandlerLeaderBoard.class);
  public PacketHandlerLeaderBoard(@NotNull String json) {
    CommandLeaderBoard commandLeaderBoard;
    try {
      commandLeaderBoard = (CommandLeaderBoard) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("failed to deserialize leaderboard command",ex);
      return;
    }
    Game.leaderBoard = commandLeaderBoard.getLeaderBoard();
  }
}
