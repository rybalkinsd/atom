package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    private int xCoord;
    private int yCoord;

    public Point(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return this.xCoord;
    }

    public int getyCoord() {
        return this.yCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;
        if (point.getxCoord() == this.getxCoord() && point.getyCoord() ==  this.getyCoord()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getyCoord() ^ this.getxCoord();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point pointTwo = (Point) other;
        if (pointTwo.getxCoord() == this.getxCoord() && pointTwo.getyCoord() == this.getyCoord()) {
            return true;
        } else {
            return false;
        }
    }
}
