package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


/**
 * Created by pavel on 06.03.17.
 */
public class TemporaryBox extends AbstractGameObject implements Positionable, Dieable {

    private static final Logger log = LogManager.getLogger(TemporaryBox.class);
    private Point position;
    private int id;
    private boolean isDead;

    public TemporaryBox(Point position) {
        this.position = position;
        this.id = AbstractGameObject.id++;
        this.isDead = false;
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
    public boolean isDead() {
        if (isDead) {
            log.info(this
                    + " is dead");
            return isDead;
        } else {
            return false;
        }
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public String toString() {
        return "TemporaryBox{"
                + "id="
                + id
                + '}';
    }
}
