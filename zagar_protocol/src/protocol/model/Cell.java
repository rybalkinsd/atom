package protocol.model;

import java.io.Serializable;

/**
 * @author apomosov
 */
public final class Cell implements Serializable {
  private final int cellId;
  private final int playerId;
  private final boolean isVirus;
  private final float size;
  private int x;
  private int y;

  public Cell(int cellId, int playerId, boolean isVirus, float size, int x, int y) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.isVirus = isVirus;
    this.size = size;
    this.x = x;
    this.y = y;
  }

  public Cell(Cell cell) {
    this.cellId = cell.cellId;
    this.playerId = cell.playerId;
    this.isVirus = cell.isVirus;
    this.size = cell.size;
    this.x = cell.x;
    this.y = cell.y;
  }

  public int getPlayerId() {
    return playerId;
  }

  public boolean isVirus() {
    return isVirus;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getCellId() {
    return cellId;
  }

  public float getSize() {
    return size;
  }

  @Override
  public String toString(){
    StringBuilder build = new StringBuilder();
    build.append("ID ").append(cellId).append("VIRUS ").append(isVirus).append("SIZE").append(size);
    return build.toString();
  }

}
