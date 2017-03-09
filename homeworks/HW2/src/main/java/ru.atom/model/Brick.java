package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by mkai on 3/6/17.
 */
public class Brick extends AbstractGameObject {
    private static final Logger logger = LogManager.getLogger(Brick.class);

    public enum BrickType {
        BREAKABLE,
        UNBREACABLE,
    }

    private BrickType type;

    public Brick(int x, int y, BrickType type) {
        super(x, y);
        this.type = type;
        logger.info("new Brick! id = {} x = {} y = {} Type = {}", getId(), x, y, type);

    }

}
