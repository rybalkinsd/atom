package ru.atom.geometry;

/**
 * Created by home on 03.03.2017.
 */

public class Bar implements Collider {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int firstX;
    private int firstY;
    private int secondX;
    private int secondY;

    public Bar(int firstX, int firstY, int secondX, int secondY) {
        this.firstX = Math.min(firstX, secondX);
        this.firstY = Math.min(firstY, secondY);
        this.secondX = Math.max(firstX,secondX);
        this.secondY = Math.max(firstY,secondY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;

        return (this.firstX == bar.firstX
                 && this.firstY == bar.firstY
                 && this.secondY == bar.secondY
                 && this.secondX == bar.secondX);

    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return point.getX() >= this.firstX
                    && point.getY() >= this.firstY
                    && point.getX() <= this.secondX
                    && point.getY() <= this.secondY;
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return !((this.secondX < bar.firstX)
                    || (this.secondY < bar.firstY)
                    || (this.firstX > bar.secondX)
                    || (this.firstY > bar.secondY));
        }
        return false;
    }
}
