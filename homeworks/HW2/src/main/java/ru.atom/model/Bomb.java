package ru.atom.model;

public class Bomb extends GameObjectAbstract implements Tickable {
    private int timer = 10;

    public Bomb(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        timer -= elapsed;
        if (timer <= 0) {
            blowUp();
            isActive = false;
        }
    }

    private void blowUp() {

    }
}
