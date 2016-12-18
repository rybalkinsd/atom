package protocol.model;

import com.google.gson.annotations.Expose;

/**
 * @author apomosov
 */
public final class Cell {
  @Expose
  private final int cellId;
  @Expose
  private final int playerId;
  @Expose
  private final boolean isVirus;
  @Expose
  private final float size;
  @Expose
  private int x;
  @Expose
  private int y;

  public Cell(int cellId, int playerId, boolean isVirus, float size, int x, int y) {
    this.cellId = cellId;
    this.playerId = playerId;
    this.isVirus = isVirus;
    this.size = size;
    this.x = x;
    this.y = y;
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
}
