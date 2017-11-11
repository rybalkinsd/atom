package ru.atom.model;

public abstract class Bonus extends AbstractGameObject implements Tickable {

    private static final int lifetime = 3000;
    private int timer = 0;

    protected Bonus(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        timer += elapsed;
        if (timer >= Bonus.lifetime) {
            //delete object
        }
    }
}
