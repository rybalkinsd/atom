package protocol.model;

public final class Food {
  private int x;
  private int y;

  public Food(int _x, int _y){
    x = _x;
    y = _y;
  }

  public int getX(){ return x; }
  public int getY(){ return y; }
}