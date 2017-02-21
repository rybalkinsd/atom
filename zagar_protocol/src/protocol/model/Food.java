package protocol.model;

/**
 * @author apomosov
 */
public final class Food {
  private int cellId;
  private int x;
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
