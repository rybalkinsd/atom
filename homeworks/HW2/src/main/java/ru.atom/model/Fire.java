package  ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Fire bonus, increments player's bombs strenght
 */
public class Fire extends Bonus implements Positionable {

    public Fire(int id, Point position) {
        super(id, position);
        log.info("Fire id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    public Fire(int id, int x, int y) {
        super(id, x, y);
        log.info("Fire id={} x={} y={} created",
                id, position.getX(), position.getY());
    }
}