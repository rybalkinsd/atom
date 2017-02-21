package model;

/**
 * @author apomosov
 */
public class Food extends Cell {
  public Food(int x, int y) {
    super(x, y, GameConstants.FOOD_MASS);
  }
}
