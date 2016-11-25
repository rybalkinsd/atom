package utils;

import model.Field;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 */
public class UniformFoodGenerator implements FoodGenerator {
  @NotNull
  private final Field field;
  private final int threshold;
  private final double foodPerSecond;

  public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold) {
    this.field = field;
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
  }

  @Override
  public void tick(long elapsedNanos) {
    if (field.getFoods().size() <= threshold) {
      int toGenerate = (int) Math.ceil(foodPerSecond * elapsedNanos / 1_000_000_000.);
    }
  }
}
