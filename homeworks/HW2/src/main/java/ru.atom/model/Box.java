package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Box implements Positionable {

    private final Point position;
    private final int boxId;
    private final Type type; //выбираем, стена это или ящик
    private static final Logger log = LogManager.getLogger(Box.class);

    public Box(final Point position,Type type) {
        this.position = position;
        this.boxId = GameSession.getBoxId();
        this.type = type;
        log.info("New box was created: id={}, position = ({},{})", boxId, position.getX(), position.getY());
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return boxId;
    }

    public enum Type {
        TMP, NotTMP //ломается или нет
    }
}
