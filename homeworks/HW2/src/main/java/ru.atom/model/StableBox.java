package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


/**
 * Created by pavel on 06.03.17.
 */
public class StableBox extends AbstractGameObject implements Positionable {

    private static final Logger log = LogManager.getLogger(StableBox.class);
    private int id;
    private Point position;

    public StableBox(Point position) {
        this.position = position;
        this.id = AbstractGameObject.id++;
        log.info(this
                + " was created");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "StableBox{"
                + "id="
                + id
                + '}';
    }
}
