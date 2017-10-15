package  ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box implements Positionable {

    private static final Logger log = LogManager.getLogger(Box.class);
    private int id;
    private Point position;

    public Box(int id, Point position) {
        this.id = id;
        this.position = position;
        log.info("Box id={} x={} y={} created",
                id, position.getX(), position.getY());
    }

    public Box(int id, int x, int y) {
        this.id = id;
        position = new Point(x,y);
        log.info("Box id={} x={} y={} created",
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