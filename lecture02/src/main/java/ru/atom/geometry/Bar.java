package ru.atom.geometry;

/**
 * Created by home on 03.03.2017.
 */

public class Bar implements Collider {
    
    private int x;
    private int y;

    private Point justPoint = new Point(x, y);

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
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        if (x >= this.firstX && x <= this.secondX && y >= this.firstY && y <= this.secondY) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return ((Point) other).getX() >= this.firstX
                    && ((Point) other).getY() >= this.firstY
                    && ((Point) other).getX() <= this.secondX
                    && ((Point) other).getY() <= this.secondY;
        }

        if (other instanceof Bar) {
            return ((Bar) other).justPoint.getX() > this.firstX
                    || ((Bar) other).justPoint.getY() > this.firstY
                    || ((Bar) other).justPoint.getX() < this.secondX
                    || ((Bar) other).justPoint.getY() < this.secondY;
        }
        return false;
    }
}
