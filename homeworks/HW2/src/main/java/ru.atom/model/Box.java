package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Box implements Positionable {

    private static final Logger logger = LogManager.getLogger(Box.class);

    private final int id;
    private final Point position;

    private Buff.BuffType buffType = null;

    public Box(int id, Point position) {
        this.id = id;
        this.position = position;

        logger.info(toString());
    }

    public void setBuff(Buff.BuffType buffType) {
        this.buffType = buffType;
    }

    public boolean containsBuff() {
        return buffType != null;
    }

    public Buff getBuff() {
        if (buffType == null) {
            throw new NullPointerException("the buffType field is null");
        }
        return new Buff(id, position, buffType); // FIXME: needs to generate new id!!!
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[Box: id=" + String.valueOf(id) + " pos=" + position.toString() + "]";
    }
}
