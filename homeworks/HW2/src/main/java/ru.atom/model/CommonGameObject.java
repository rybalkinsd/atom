package ru.atom.model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public abstract class CommonGameObject implements Positionable {
    private static final Logger logger = LogManager.getLogger(GameSession.class);

    private final int id;
    private Point position;

    public CommonGameObject(int x, int y) {
        if(x <0 || y < 0) {
         logger.error("Objects coordinates must be > 0");
         throw new IllegalArgumentException();
        }
        this.id = Gamesession.setObject();
        this.position = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition() {
        position = new Pont(x, y);
    }

    public void setPosition(Point newPosiiton) {
        position = newPosiiton;
    }
}