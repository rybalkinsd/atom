package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Boots extends Bonus {

    private static final Logger log = LogManager.getLogger(Boots.class);

    public Boots() {
        super();
        log.info("boots object created");
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
