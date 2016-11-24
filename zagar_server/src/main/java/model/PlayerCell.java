package model;

import utils.IDGenerator;
import utils.SequentialIDGenerator;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
  private final int id;

  public PlayerCell(int id, int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
