package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Artem on 10/11/16.
 *
 * Class representing food on the map
 */
public class Food extends GameObject {

    private static final Logger log = LogManager.getLogger(Food.class);

    @Override
    public String toString() {
        return "Food{" +
                "coordinates='" + x + ' ' + y + '\'' + "  radius='" + radius + '\'' + " color='"
                + color.toString() + '\'' + '}';
    }

    public Food() {
        super(GameConstants.RADIUS_OF_FOOD);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Food(double x, double y) {
        super(x, y, GameConstants.RADIUS_OF_FOOD);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

}
