package ru.atom.model;

public class Bomb extends AbstractGameObject implements Tickable {

    private static final int lifetime = 3000;
    private int timer = 0;

    public Bomb(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        timer += elapsed;
        if (timer >= Bomb.lifetime) {
            //delete object
        }
    }
}
