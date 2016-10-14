package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by valentin on 10.10.16.
 */

public class Food {
    private static final Logger log = LogManager.getLogger(Player.class);
    private static Color color;
    private Coordinate coordinate;
    /**
     * Create new Food
     *
     * @param x             x-coordinate
     * @param y             y-coordinate
     * @param color         Color
     */
    public Food(Double x, Double y, Color color){
        coordinate = new Coordinate(x,y);
        this.color = color;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }
    @Override
    public String toString() {
        return "Food{" +
                " Color = " + color.name() + "; Coordinates = " + coordinate.toString() +
                '}';
    }
}
