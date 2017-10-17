package ru.atom.model;

public abstract class Bonus extends AbstractGameObject implements Tickable {

    protected Bonus(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {

    }
}
