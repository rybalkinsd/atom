package utils;

import model.Field;
import model.Food;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author apomosov
 */
public class UniformFoodGenerator implements FoodGenerator {
  @NotNull
  private final Field field;
  private final int threshold;
  private final double foodPerSecond;

  @NotNull
  private static Random generator = new Random();

  public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold) {
    this.field = field;
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
  }

  @Override
  public void tick(long elapsedNanos) {
    if (field.getFoods().size() <= threshold) {
      int toGenerate = (int) Math.ceil(foodPerSecond * elapsedNanos / 1_000_000_000);

      for(int i = 0; i < toGenerate; i++){
        field.getFoods().add(new Food(generator.nextInt(field.getWidth()), generator.nextInt(field.getHeight())));
      }
    }
  }
}
