package zagar.network.handlers;

import zagar.Game;

public class PacketHandlerAuthOk {
  public PacketHandlerAuthOk() {
    Game.state = Game.GameState.AUTHORIZED;
  }
}
