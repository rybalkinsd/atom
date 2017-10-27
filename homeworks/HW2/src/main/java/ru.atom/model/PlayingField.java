package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class PlayingField implements Positionable {

    private Point pointOfPlayingField;
    private int id;

    private static final Logger log = LogManager.getLogger(PlayingField.class);

    public PlayingField() {
        pointOfPlayingField = new Point(0, 0);
        id = 0;
        log.info("playingField object created");
    }

    @Override
    public Point getPosition() {
        return pointOfPlayingField;
    }

    @Override
    public int getId() {
        return id;
    }
}
