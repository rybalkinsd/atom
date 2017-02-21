package protocol.model;

/**
 * @author apomosov
 */
public final class Food {
  private int x;
  private int y;

  public Food(int x, int y){
    this.x = x;
    this.y = y;
  }

  public int getY(){
    return y;
  }

  public int getX(){
    return x;
  }

}
