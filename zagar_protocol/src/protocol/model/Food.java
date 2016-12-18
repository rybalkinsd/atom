package protocol.model;

import com.google.gson.annotations.Expose;

/**
 * @author apomosov
 */
public final class Food {
  @Expose
  private int x;
  @Expose
  private int y;

  public Food(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
}
