package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Artem on 10/11/16.
 *
 * Class representing obstacle
 */
public class Obstacle extends GameObject{

    public Obstacle(){
        super(GameConstants.RADIUS_OF_OBSTACLE);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Obstacle(double x, double y){
        super(x, y, GameConstants.RADIUS_OF_OBSTACLE);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public Obstacle(double x, double y, double radius) {
        super(x, y, radius);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public void changeRadius(int amountOfFood) {
        this.radius += amountOfFood;
    }

    private static final Logger log = LogManager.getLogger(Obstacle.class);

    @Override
    public String toString() {
        return "Obstacle{" +
                "coordinates='" + x + ' ' + y + '\'' + "  radius='" + radius + '\'' +
                " color='" + color.toString() + '\'' + '}';
    }
}