package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;

public class Fire extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Bomb.class);

    public Fire(GeomObject geomObject) {
        super(geomObject);
        log.info(this.toString());
    }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"type\":\"Fire\"" +
                "}";
    }

    @Override
    public Rectangle getForm() {
        return (Rectangle)geomObject;
    }
}

