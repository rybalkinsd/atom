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
        log.info(this.toString());
    }

    public FeedType getType() {
        return type;
    }

    public enum FeedType {
        SPEED_BOOTS(0), AMMUNITION_INCR(1), EXPLOS_BOOST(2), EMPTY(3);
        private final int value;
        private FeedType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"typePosition\":" + type.getValue() +
                ",\"type\":\"Bonus\"" +
                "}";
    }
}

