package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Box.class);
    private  Feed.FeedType feedType;

    BoxCollapseListener listener = null;

    public boolean addBoxCollapseListener(BoxCollapseListener boxCollapseListener) {
        if (this.listener == null) {
            this.listener = boxCollapseListener;
            return true;
        }
        return false;
    }

    public void removeBoxCollapseListener() {
        listener = null;
    }


    public Box(GeomObject geomObject, Feed.FeedType feedType) {
        super(geomObject);
        this.feedType = feedType;
        log.info(this.toString());
    }

    public void collapse() {
        listener.handleBoxCollapse(this);
    }

    public Feed.FeedType getFeedType() {
        return feedType;
    }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"type\":\"Wood\"" +
                "}";
    }

    @Override
    public Rectangle getForm() {
        return (Rectangle)geomObject;
    }
}
