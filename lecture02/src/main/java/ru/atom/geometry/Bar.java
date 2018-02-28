package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstCornerX < secondCornerX) {
            this.firstCornerX = firstCornerX;
            this.secondCornerX = secondCornerX;
        } else {
            this.firstCornerX = secondCornerX;
            this.secondCornerX = firstCornerX;
        }
        if (firstCornerY < secondCornerY) {
            this.firstCornerY = firstCornerY;
            this.secondCornerY = secondCornerY;
        } else {
            this.firstCornerY = secondCornerY;
            this.secondCornerY = firstCornerY;
        }
    }

    public int getFirstCornerX() { return firstCornerX; }

    public int getFirstCornerY() { return firstCornerY; }

    public int getSecondCornerX() { return secondCornerX; }

    public int getSecondCornerY() { return secondCornerY; }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point p = (Point) other;
            return p.getX() >= getFirstCornerX() && p.getX() <= getSecondCornerX() &&
                   p.getY() >= getFirstCornerY() && p.getY() <= getSecondCornerY();
        } else if (other instanceof Bar) {
            Bar b = (Bar) other;
            return !(getSecondCornerX() < b.getFirstCornerX() ||
                     b.getSecondCornerX() < getFirstCornerX() ||
                     getSecondCornerY() < b.getFirstCornerY() ||
                     b.getSecondCornerY() < getFirstCornerY());
        } else
            return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar b = (Bar) o;
        return getFirstCornerX() == b.getFirstCornerX() && getFirstCornerY() == b.getFirstCornerY() &&
               getSecondCornerX() == b.getSecondCornerX() && getSecondCornerX() == b.getSecondCornerX();
    }
}
