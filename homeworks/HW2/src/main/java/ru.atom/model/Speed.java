package  ru.atom.model;

import ru.atom.geometry.Point;

public class Speed extends Bonus implements Positionable {

    public Speed(int id, Point position) {
        super(id, position);
        log.info("Speed id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    public Speed(int id, int x, int y) {
        super(id, x, y);
        log.info("Speed id={} x={} y={} created",
                id, position.getX(), position.getY());
    }
}