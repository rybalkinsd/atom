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

  protected void move(double direction, double velocity){
    int x = this.getX() + (int) Math.round(Math.cos(direction)*velocity);
    if (x < 0){
      x = 0;
    }
    if (x > GameConstants.FIELD_WIDTH){
      x = GameConstants.FIELD_WIDTH;
    }
    int y = this.getY() - (int) Math.round(Math.sin(direction)*velocity);
    if (y < 0){
      y = 0;
    }
    if (y > GameConstants.FIELD_HEIGHT){
      y = GameConstants.FIELD_HEIGHT;
    }

    this.setX(x);
    this.setY(y);
  }

  public int getId() {
    return id;
  }
}
