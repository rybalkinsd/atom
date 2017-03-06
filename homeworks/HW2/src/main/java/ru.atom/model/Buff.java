package ru.atom.model;

import ru.atom.geometry.Point;

public class Buff implements Positionable {
    private final int id;
    private Point position;
    Buffs type = Buffs.BOOST;

    public enum Buffs {
        BOOST, RANGE, UNLIMIT
    }

    public Buff(int x, int y, Buffs type) {
        this.position = new Point(x, y);
        this.type = type;
        this.id = GameSession.nextId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
