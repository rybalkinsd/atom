package utils;

import matchmaker.MatchMakerImpl;
import model.Field;
import model.Food;
import model.GameConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.ThreadLocalRandom;

import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 */
public class UniformFoodGenerator implements FoodGenerator {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerImpl.class);

  @NotNull
  private final Field field;
  private final int threshold;
  private final double foodPerSecond;

  public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold) {
    this.field = field;
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
  }

  public Food betterFoodPlacing(){
    boolean isNotCapable;
    Food foodInstance;
    do {
      int x = ThreadLocalRandom.current().nextInt(0, GameConstants.FIELD_WIDTH + 1);
      int y = ThreadLocalRandom.current().nextInt(0, GameConstants.FIELD_HEIGHT + 1);
      foodInstance = new Food(x, y);
      isNotCapable = false;
      for (Food e: field.getFoods()){
        if (e.equals(foodInstance)){
          isNotCapable = true;
        }
      }


    } while (isNotCapable);
    return foodInstance;
  }

  public Food randomFoodPlacing(){
    int x = ThreadLocalRandom.current().nextInt(0, GameConstants.FIELD_WIDTH + 1);
    int y = ThreadLocalRandom.current().nextInt(0, GameConstants.FIELD_HEIGHT + 1);
    return new Food(x, y);
  }

  @Override
  public void tick(long elapsedNanos) {
    if (field.getFoods().size() <= threshold) {
      int toGenerate = (int) Math.ceil(foodPerSecond * elapsedNanos / 1_000_000_000.);
      for (int i = 0; i < Math.min(threshold - field.getFoods().size(), toGenerate); i++){
        field.addFood(betterFoodPlacing());
      }
    }
  }
}
