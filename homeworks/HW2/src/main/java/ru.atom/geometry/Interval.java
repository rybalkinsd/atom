package ru.atom.geometry;

public class Interval implements Collider {
    private final int p1;
    private final int p2;

    public Interval(int p1, int p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public int getP1() {
        return this.p1;
    }

    public int getP2() {
        return this.p2;
    }

    public boolean isColliding(Collider o) {
        if (!(o instanceof Interval)) {
            return false;
        }
        Interval other = (Interval) o;

        if (this.p1 <= other.getP2() && this.p2 >= other.getP1()) {
            return true;
        }
        return false;

    }
}