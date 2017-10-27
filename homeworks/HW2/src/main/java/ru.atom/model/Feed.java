package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Feed extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Feed.class);
    private FeedType type;

    public Feed(GeomObject geomObject, FeedType type) {
        super(geomObject);
        this.type = type;
        log.info(" [id = " + this.getId() + "] Created: Feed( " + geomObject.toString() +
                ", feed = " + type.name() +
                " )");
    }

    public FeedType getType() {
        return type;
    }

    public enum FeedType { SPEED_BOOTS, AMMUNITION_INCR, EXPLOS_BOOST, EMPTY }
}

