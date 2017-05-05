package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by BBPax on 06.03.17.
 */
public class AbstractGameObject implements Positionable {
    private static final Logger log = LogManager.getLogger(AbstractGameObject.class);
    protected String type;
    private int id;
    protected Point position;

    public AbstractGameObject(int id, int x, int y) {
        if (x < 0 || y < 0) {
            log.error("Wrong coordinates of creating objects");
            throw new IllegalArgumentException();
        }
        type = "AbstractGameObject";
        this.id = id;
        this.position = new Point(x,y);
        //log.info("AbstractGameObject was created with coordinates: ( {} ; {} )", x, y);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int newId) {
        id = newId;
    }
}
