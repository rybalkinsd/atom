package utils;

import model.Location;
import model.Food;
import model.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Ticker;

import java.util.Random;

/**
 * @author apomosov
 */
public class UniformFoodGenerator implements FoodGenerator {
    @NotNull
    private final GameField field;
    private final int threshold;
    private final double foodPerSecond;

    private final static Logger log = LogManager.getLogger(UniformFoodGenerator.class);

    public UniformFoodGenerator(@NotNull GameField field, double foodPerSecond, int threshold) {
        this.field = field;
        this.threshold = threshold;
        this.foodPerSecond = foodPerSecond;
    }

    @Override
    public void tick(long elapsedNanos) {
        if (field.getFoods().size() <= threshold) {
            int toGenerate = (int) Math.floor(foodPerSecond * elapsedNanos / 1_000_000_000.);
            Random random = new Random();
            for(int i = 0; i < toGenerate; i ++){
                Food newFood = new Food(new Location(0,0));
                Location newLocation = new Location(newFood.getRadius() + random.nextInt(field.getWidth() - 2 * newFood.getRadius()),
                        newFood.getRadius() + random.nextInt(field.getHeight() - 2 * newFood.getRadius()));
                newFood.setLocation(newLocation);
                field.getFoods().add(newFood);
                field.getFoodsToAdd().add(newFood);
            }
            log.info(toGenerate + " food generated");
        }
    }


    @Override
    public void run() {
        Ticker ticker = new Ticker(this, 1);
        ticker.loop();
    }
}
