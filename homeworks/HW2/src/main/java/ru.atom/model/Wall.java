package ru.atom.model;

import org.ietf.jgss.Oid;
import ru.atom.geometry.Point;

/**
 * Created by kinetik on 08.03.17.
 */
public class Wall implements Positionable, Tickable, GameObject {

    private final int wallId;
    private long tickValue;
    private Point position;
    private final boolean destractbl;

    public Wall(int wallId, long tickValue, Point position, boolean destractbl) {
        this.wallId = wallId;
        this.tickValue = tickValue;
        this.position = position;
        this.destractbl = destractbl;
    }

    @Override
    public int getId() {
        return this.wallId;
    }

    @Override
    public void tick(long elapsed) {
        this.tickValue = this.tickValue + elapsed;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
