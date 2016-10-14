package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by valentin on 10.10.16.
 */
public class Bomb{
    private static final Logger log = LogManager.getLogger(Player.class);
    private Double radius;
    private Coordinate coordinate;
    /**
     * Create new Food
     *
     * @param x             x-coordinate
     * @param y             y-coordinate
     * @param rad           radius
     */
    public Bomb(Double x, Double y, Double rad){
        this.coordinate = new Coordinate(x,y);
        this.radius = rad;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }
    @Override
    public String toString() {
        return "Bomb{" +
                " Coordinates = " + coordinate.toString() + "; Radius = " + this.radius.toString() +
                '}';
    }
}
