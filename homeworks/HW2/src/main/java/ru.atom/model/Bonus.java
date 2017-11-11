package ru.atom.model;

public abstract class Bonus extends AbstractGameObject implements Tickable {

    private static final int lifetime = 3000;

    protected Bonus(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        if (elapsed >= Bonus.lifetime) {
            //do something
        }
    }
}
