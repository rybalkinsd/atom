package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;

/**
 * Created by BBPax on 06.03.17.
 */
public class AbstractGameObject implements Positionable {
    private static final Logger log = LogManager.getLogger(AbstractGameObject.class);
    protected String type;
    private int id;
    protected Point position;
    @JsonIgnore
    protected Bar bar;

    public AbstractGameObject(int id, int x, int y) {
        if (x < 0 || y < 0) {
            log.error("Wrong coordinates of creating objects");
           // throw new IllegalArgumentException();
        }
        type = "AbstractGameObject";
        this.id = id;
        this.position = new Point(x,y);
        this.bar = new Bar(position, 32);
        //log.info("AbstractGameObject was created with coordinates: ( {} ; {} )", x, y);
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public Bar getBar() {
        return bar;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setId(int newId) {
        id = newId;
    }
}
