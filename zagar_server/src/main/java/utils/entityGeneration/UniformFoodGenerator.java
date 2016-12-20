package utils.entityGeneration;

import model.Field;
import model.Food;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author apomosov
 *
 * Uniform (same form) food generator
 * Generates given number {@link Food} cells per second until treshold will be reached
 */
public class UniformFoodGenerator extends FoodGenerator {
    private final int threshold;
    private final double foodPerSecond;
    private final double removeChance;

    /**
     * @param field field where {@link Food} cells will be generated
     * @param foodPerSecond number of food which will be generated per second
     * @param threshold maximum number of {@link Food} cells on field
     * @param removeChance random number of food will be removed with this probability
     */
    public UniformFoodGenerator(@NotNull Field field, double foodPerSecond, int threshold, double removeChance) {
        super(field);
        this.threshold = threshold;
        this.foodPerSecond = foodPerSecond;
        this.removeChance = removeChance;
        assert (removeChance >= 0 && removeChance <= 1);
    }

    /**
     * Generator logic
     * Stages:
     * 1) Decide remove or not remove foods
     * 2) Determine number of removed foods
     * 3) Remove foods
     * 4) Determine number of generated viruses to reach threshold
     * 5) Generate food
     * @param elapsed tick time interval
     */
    @Override
    public void generate(@NotNull Duration elapsed) {
        //Remove or not?
        Random rand = new Random();
        if (rand.nextDouble() > 1 - removeChance) {
            List<Food> foods = new ArrayList<>(getField().getCells(Food.class));
            int toRemove = (int) (foods.size() * rand.nextDouble());
            for (int i = 0; i < toRemove; i++) {
                getField().removeCell(foods.get(i));
            }
        }
        if (getField().getCells(Food.class).size() <= threshold) {
            for (int i = 0; i < foodPerSecond; i++) {
                Food food = new Food(new Point2D.Double(0,0));
                food.setCoordinate(new Point2D.Double(
                        food.getRadius() +
                                rand.nextInt((int)(getField().getSize().getWidth() - 2 * food.getRadius())),
                        food.getRadius() +
                                rand.nextInt((int)(getField().getSize().getHeight() - 2 * food.getRadius()))
                ));
                getField().addCell(food);
            }
        }
    }
}
