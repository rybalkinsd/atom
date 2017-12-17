package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.listners.BoxCollapseListener;

import java.util.Vector;

public class Box extends FormedGameObject {
    private static final Logger log = LogManager.getLogger(Box.class);
    private  Feed.FeedType feedType;

    Vector<BoxCollapseListener> listeners = new Vector<>();

    public void addBoxCollapseListener(BoxCollapseListener boxCollapseListener) {
        this.listeners.add(boxCollapseListener);

    }

    public Box(GeomObject geomObject, Feed.FeedType feedType) {
        super(geomObject);
        this.feedType = feedType;
        log.info(this.toString());
    }

    public void collapse() {
        listeners.forEach(boxCollapseListener -> {
            boxCollapseListener.handleBoxCollapse(this);
        });
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
