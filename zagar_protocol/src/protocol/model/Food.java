package protocol.model;

import java.io.Serializable;

/**
 * @author apomosov
 */
public final class Food implements Serializable{
  private int x;
  private int y;

  public Food(Food food){
    this.x=food.x;
    this.y=food.y;
  }

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

  @Override
  public String toString(){
    return "Food{" +
            "x=" + x +
            ",y=" + y +
            "}";
  }

}
