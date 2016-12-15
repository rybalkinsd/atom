package zagar.network.handlers;

import org.jetbrains.annotations.NotNull;
import protocol.CommandReplicate;
import zagar.Game;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;
import zagar.view.Cell;
import zagar.view.Food;

public class PacketHandlerReplicate {

  public PacketHandlerReplicate(@NotNull String json) {
    CommandReplicate commandReplicate;
    try {
      commandReplicate = JSONHelper.fromJSON(json, CommandReplicate.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    Game.player.clear();
    Cell[] gameCells = new Cell[commandReplicate.getCells().length];
    for (int i = 0; i < commandReplicate.getCells().length; i++) {
      protocol.model.Cell c = commandReplicate.getCells()[i];
      gameCells[i] = new Cell(c.getX(), c.getY(), c.getSize(), c.getCellId(), c.isVirus(), c.getName());
      if (c.getPlayerId() == Game.playerId){
        Game.player.add(gameCells[i]);
      }
    }
    Game.cells = gameCells;
    Food[] gameFood = new Food[commandReplicate.getFood().length];
    for (int i = 0; i < commandReplicate.getFood().length; i++) {
      protocol.model.Food f = commandReplicate.getFood()[i];
      if (f != null) {
        gameFood[i] = new Food(f.getX(), f.getY());
      }
    }
    Game.food = gameFood;
  }
}