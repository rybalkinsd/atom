package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = Math.min(firstCornerX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(firstCornerX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return bar.getFirstCornerX() == getFirstCornerX() &&
                bar.getFirstCornerY() == getFirstCornerY() &&
                bar.getSecondCornerX() == getSecondCornerX() &&
                bar.getSecondCornerY() == getSecondCornerY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return Utils.barIsCollidingWithPoint(this, (Point) other);
        } else if (other instanceof Bar) {
            return isCollidingWithBar((Bar) other);
        }

        return false;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    private boolean isCollidingWithBar(Bar other) {
        Point pointLeftDown = (Point) Geometry.createPoint(other.getFirstCornerX(), other.getFirstCornerY());
        Point pointLeftUp = (Point) Geometry.createPoint(other.getFirstCornerX(), other.getSecondCornerY());
        Point pointRightDown = (Point) Geometry.createPoint(other.getSecondCornerX(), other.getFirstCornerY());
        Point pointRightUp = (Point) Geometry.createPoint(other.getSecondCornerX(), other.getSecondCornerY());

        return Utils.barIsCollidingWithPoint(this, pointLeftDown) ||
                Utils.barIsCollidingWithPoint(this, pointLeftUp) ||
                Utils.barIsCollidingWithPoint(this, pointRightDown) ||
                Utils.barIsCollidingWithPoint(this, pointRightUp);
    }
}
