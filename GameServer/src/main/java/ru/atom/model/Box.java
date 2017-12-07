package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Box.class);
    private  Feed.FeedType feedType;

    public Box(GeomObject geomObject, Feed.FeedType feedType) {
        super(geomObject);
        this.feedType = feedType;
        log.info(" [id = " + this.getId() + "] Created: Box( " + geomObject.toString() +
                ", feedType = " + feedType.name() +
                " )");
    }

    public void collapse() {
    }

    public Feed.FeedType getFeedType() {
        return feedType;
    }
}
