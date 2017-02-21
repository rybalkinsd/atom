package zagar.network.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandAuthOk;
import zagar.Game;
import zagar.util.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuthOk {
  private static final Logger log = LogManager.getLogger(PacketHandlerAuthOk.class);
  public PacketHandlerAuthOk(String json) {

    CommandAuthOk commandAuthOk;
    try {
      commandAuthOk = (CommandAuthOk) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("failed to deserialize auth ok command",ex);
      return;
    }
    Game.id = commandAuthOk.getId();
    Game.state = Game.GameState.AUTHORIZED;
  }
}
