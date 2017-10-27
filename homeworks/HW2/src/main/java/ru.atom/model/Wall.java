package ru.atom.model;

import ru.atom.geometry.GeomObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Wall.class);

    public Wall(GeomObject geomObject) {
        super(geomObject);
        log.info(" [id = " + this.getId() + "] Created: Wall( " + geomObject.toString() + " )");
    }
}
