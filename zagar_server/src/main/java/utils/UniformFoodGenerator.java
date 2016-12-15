package utils;

import model.Field;
import model.Food;
import model.GameConstants;
import org.eclipse.jetty.util.ConcurrentHashSet;
import java.util.Random;

public class UniformFoodGenerator implements FoodGenerator {
  private Field field;
  private final int threshold;
  private final double foodPerSecond;

  public UniformFoodGenerator(double foodPerSecond, int threshold) {
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
  }

  @Override
  public void setField(Field field){
    this.field = field;
  }

  @Override
  public void tick(long elapsedNanos) {
    try {
      if (field.getFoods().size() <= threshold) {
        Random random = new Random();
        int foodRadius = (int) Math.sqrt(GameConstants.FOOD_MASS / Math.PI);
        ConcurrentHashSet<Food> food = field.getFoods();
        for (int i = 0; i < 4/*foodPerSecond*/; i++) {
          food.add(new Food(
                  foodRadius + random.nextInt(field.getWidth() - 2 * foodRadius),
                  foodRadius + random.nextInt(field.getHeight() - 2 * foodRadius)
          ));
        }
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }
}