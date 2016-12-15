package utils;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Ticker;

import java.util.Random;

/**
 * @author apomosov
 */
public class UniformFoodGenerator  implements FoodGenerator {
  @NotNull
  private final static Logger log = LogManager.getLogger(UniformFoodGenerator.class);

  @NotNull
  private final Field field;
  private final int threshold;
  private final double foodPerSecond;
  public int virustime;

  public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold) {
    this.field = field;
    this.threshold = threshold;
    this.foodPerSecond = foodPerSecond;
    this.virustime=0;
  }

  public void run() {
    log.info("UniformFoodGenerator " + " started");
     new Thread(()->{
       Ticker ticker = new Ticker(this, (int)Math.ceil(foodPerSecond));
       ticker.loop();
     }).start();
  }

  @Override
  public void tick(long elapsedNanos) {

    if (field.getFoodSet().size() < threshold) {
      Random random = new Random();
      random.nextInt(field.getWidth() - 2);
      random.nextInt(field.getHeight() - 2);

      Food food = new Food(
              random.nextInt(field.getWidth() - 2),
              random.nextInt(field.getHeight() - 2)
      );
      field.addFood(food);

        virustime++;
        if (virustime == 10) {
          if (field.getViruses().size() < GameConstants.NUMBER_OF_VIRUSES) {
            Virus virus = new Virus(
                    random.nextInt(field.getWidth() - 2),
                    random.nextInt(field.getHeight() - 2)
            );
            field.addVirus(virus);
          }
          virustime = 0;
        }

//      log.info("UniformFoodGenerator ticked, field size now {}", field.getFoodSet().size());
      //int toGenerate = (int) Math.ceil(foodPerSecond * elapsedNanos / 1_000_000_000.);
    }
  }
}
