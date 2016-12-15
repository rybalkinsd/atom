package zagar.network.handlers;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CommandReplicate;
import zagar.util.Colors;
import zagar.util.JSONDeserializationException;
import zagar.util.JSONHelper;
import zagar.view.Cell;
import zagar.Game;
import org.jetbrains.annotations.NotNull;

public class PacketHandlerReplicate {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketHandlerReplicate.class);

  public PacketHandlerReplicate(@NotNull String json) {
    CommandReplicate commandReplicate;
    try {
      commandReplicate = JSONHelper.fromJSON(json, CommandReplicate.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }

    Cell[] gameCells = new Cell[commandReplicate.getCells().length];
    Game.playerCells.clear();
    for (int i = 0; i < commandReplicate.getCells().length; i++) {
      protocol.model.Cell c = commandReplicate.getCells()[i];
      gameCells[i] = new Cell(c.getX(), c.getY(), c.getSize(), c.getCellId(), c.isVirus());
      // ROTATION FIX
      for (Cell c2 : Game.cells) {
        if (c2 != null && c2.id == c.getCellId()) {
          gameCells[i].setRotationAngle(c2.getRotationAngle());
          break;
        }
      }

      // COLORIZATION
      Colors color = Colors.DEEP_PURPLE;
      if (c.getPlayerId() != -1) {
        Game.playerColors.putIfAbsent(c.getPlayerId(), Colors.getRandom());
        color = Game.playerColors.get(c.getPlayerId());
      }
      gameCells[i].setColor(color);

      if (c.getPlayerId() == Game.playerID) {
        log.debug("Player cell added");
        Game.playerCIDs.add(c.getCellId());
      }
    }

    if (Game.playerID == 0 && Game.playerCIDs.size() == 0) {
      log.warn("playerID might be uninitialized");
    }

    Game.cells = gameCells;

    Cell[] foods = new Cell[commandReplicate.getFood().length];
    for (int i = 0; i < commandReplicate.getFood().length; i++) {
      protocol.model.Food f = commandReplicate.getFood()[i];

      if(f != null) {
        foods[i] = new Cell(f.getX(), f.getY(), 10f, -1, false);
        foods[i].setRotating(false);
        foods[i].setStaticVerges(20);
        foods[i].setColor(Colors.CYAN);
      }
    }

    Game.foods = foods;
  }
}
