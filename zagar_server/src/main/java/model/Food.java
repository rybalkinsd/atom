package model;

/**
 * @author apomosov
 */
public class Food extends Cell implements Eatable {
  public Food(int x, int y) {
    super(x, y, GameConstants.FOOD_MASS);
  }
}
