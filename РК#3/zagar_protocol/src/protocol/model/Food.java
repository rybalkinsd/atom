package protocol.model;

/**
 * @author apomosov
 */
public final class Food {
  private int x;
  private int y;

  public Food(int x, int y){
    this.x=x;
    this.y=y;
  }

  public Food setX(int x){
    this.x=x;
    return this;
  }

  public Food setY(int y){
    this.y=y;
    return this;
  }

  public int getX(){
    return this.x;
  }
  public int getY(){
    return this.y;
  }

}
