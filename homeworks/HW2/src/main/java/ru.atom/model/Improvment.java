package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Improvment implements Positionable {

    public enum ImprovmentType {
        SPEED, BOMBMAX, EXPLOSIONRANGE
    }

    private static final Logger log = LogManager.getLogger(Improvment.class);
    private final Point coordinates;
    private final ImprovmentType type;
    private final int id;

    public Improvment(Point coordinates, ImprovmentType type) {
        this.coordinates = coordinates;
        this.type = type;
        this.id = GameSession.nextId();

        log.info("Player: id={},position({},{}),type={}\n", this.id, coordinates.getX(), coordinates.getY(), this.type);
    }

    @Override
    public Point getPosition() {
        return coordinates;
    }

    @Override
    public int getId() {
        return id;
    }
}
