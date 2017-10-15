package  ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private int id;
    private Point position;

    public Wall(int id, Point position) {
        this.id = id;
        this.position = position;
        log.info("Wall id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    public Wall(int id, int x, int y) {
        this.id = id;
        position = new Point(x,y);
        log.info("Wall id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}