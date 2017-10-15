package  ru.atom.model;

import ru.atom.geometry.Point;

public class BombBonus extends Bonus implements Positionable {

    public BombBonus(int id, Point position) {
        super(id, position);
        log.info("BombBonus id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    public BombBonus(int id, int x, int y) {
        super(id, x, y);
        log.info("BombBonus id={} x={} y={} created",
                id, position.getX(), position.getY());
    }
}