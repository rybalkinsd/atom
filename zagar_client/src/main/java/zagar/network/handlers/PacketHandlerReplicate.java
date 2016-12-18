package main.java.zagar.network.handlers;

import main.java.zagar.Game;
import main.java.zagar.util.JSONDeserializationException;
import main.java.zagar.util.JSONHelper;
import main.java.zagar.view.Cell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandReplicate;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PacketHandlerReplicate implements PacketHandler {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketHandlerReplicate.class);

  public void handle(@NotNull String json) {
    CommandReplicate commandReplicate;
    try {
      commandReplicate = JSONHelper.fromJSON(json, CommandReplicate.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }

      List<Cell> cells = commandReplicate.getCells().stream()
            .map(c -> new Cell(c.getX(), c.getY(), c.getSize(), c.getCellId(), c.isVirus(),false))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
      ConcurrentLinkedDeque<Cell> players = commandReplicate.getCells().stream()
            .filter(c -> !c.isVirus())
            .map(c -> new Cell(c.getX(), c.getY(), c.getSize(), c.getCellId(), c.isVirus(),false))
            .collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    List <Cell> food = commandReplicate.getFood().stream()
            .map(c -> new Cell(c.getX(), c.getY(), 10, 0, false, true))
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    food.forEach(e->cells.add(e));
      Game.updateBuffer(cells, players);

    //TODO
/*    if (b == null) return;
    b.order(ByteOrder.LITTLE_ENDIAN);
    short destroy = b.getShort(1);
    int offset = 3;
    for (int i = 0; i < destroy; i++) {
      for (int i2 = 0; i2 < Game.cellsNumber; i2++) {
        Cell c = Game.cells[i2];
        if (c != null) {
          if (c.id == b.getInt(offset + 4)) {
            Game.cells[i2] = null;
            if (Game.player.contains(c)) {
              Game.player.remove(c);
            }
            System.out.println("Removing " + c.id + " <" + c.name + ">");
            break;
          }
        }
      }
      offset += 8;
    }

    offset = addCell(offset, b);

    offset += 4;

    int destroyCells = b.getInt(offset);

    offset += 4;

    for (int i = 0; i < destroyCells; i++) {
      for (int i2 = 0; i2 < Game.cellsNumber; i2++) {
        Cell c = Game.cells[i2];
        if (c != null) {
          if (c.id == b.getInt(offset)) {
            Game.cells[i2] = null;
            if (Game.player.contains(c)) {
              Game.player.remove(c);
            }
            System.out.println("Removing(2) " + c.id + " <" + c.name + ">");
            break;
          }
        }
      }
      offset += 4;
    }*/
  }

  /*private int addCell(int offset, @NotNull ByteBuffer b) {
    int cellID = b.getInt(offset);
    if (cellID == 0) return offset;
    int x = b.getInt(offset + 4);
    int y = b.getInt(offset + 8);
    short size = b.getShort(offset + 12);

    byte red = b.get(offset + 14);
    byte green = b.get(offset + 15);
    byte blue = b.get(offset + 16);

    boolean flag = false;

    for (int i = 0; i < Game.cellsNumber; i++) {
      Cell c = Game.cells[i];
      if (c != null) {
        if (c.id == cellID) {
          flag = true;
        }
      }
    }

    byte flags = b.get(offset + 17);
    boolean virus = (flags & 1) == 1;

    if ((flags & 2) == 1) {
      offset += 4;
    }
    if ((flags & 4) == 1) {
      offset += 8;
    }
    if ((flags & 8) == 1) {
      offset += 16;
    }

    offset += 18;
    String name = "";
    while (b.getShort(offset) != 0) {
      name += b.getChar(offset);
      offset += 2;
    }

    if (!flag) {
      log.info("Adding new cell " + cellID + " <" + name + ">" + " /" + Game.cellsNumber + "/");
      Cell cell = new Cell(x, y, size, cellID, virus);
      if (name.length() > 0) {
        Game.cellNames.put(cellID, name);
      }
      cell.setColor(red, green, blue);
      Game.addCell(cell);
      cell.tick();
    } else {
      for (Cell cell : Game.cells) {
        if (cell != null) {
          if (cell.id == cellID) {
            cell.x = x;
            cell.y = y;
            cell.size = size;
            if (name.length() > 0) {
              cell.name = name;
              Game.cellNames.put(cellID, name);
            }
            cell.setColor(red, green, blue);
          }
        }
      }
    }

    offset += 2;
    if (b.getInt(offset) != 0) {
      offset = addCell(offset, b);
    }
    return offset;
  }*/
}
