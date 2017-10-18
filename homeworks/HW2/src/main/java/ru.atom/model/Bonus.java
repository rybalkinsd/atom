package ru.atom.model;

public abstract class Bonus extends AbstractGameObj implements Tickable {
    public Bonus(int x, int y) {
                super(x, y);
    }

    @Override
    public void tick(long elapsed) {

    }
}