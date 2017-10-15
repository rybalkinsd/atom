package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Buff implements Positionable {

    private static final Logger logger = LogManager.getLogger(Explosion.class);

    private final int id;
    private final Point position;
    private final BuffType type;

    public Buff(int id, Point position, BuffType type) {
        this.id = id;
        this.position = position;
        this.type = type;

        logger.info(toString());
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
        return "[Buff: id=" + String.valueOf(id) + " pos=" + position.toString() + " type=" + type.toString() + "]";
    }

    public enum BuffType {
        VELOCITY,    // speed of player
        POWER,    // max size of explosion line
        CAPACITY; // max numbers of bombs

        public void affect() {
            switch (this) {
                case VELOCITY:
                    break;
                case POWER:
                    break;
                case CAPACITY:
                    break;
                default:
                    break;
            }
        }
    }
}
