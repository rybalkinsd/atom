package model;

/**
 * @author apomosov
 */
public class Food extends Cell {
  public Food(int x, int y) {
    super(x, y, GameConstants.FOOD_MASS);
  }

  @Override
  public boolean equals(Object obj){
    if (!(obj instanceof Food) || (obj == null))
      return false;
    if (obj == this)
      return true;
    Food rhs = (Food) obj;
    if ((rhs.getY() == this.getY()) &&(rhs.getX() == this.getX())){
      return true;
    }
    return false;
  }
}
