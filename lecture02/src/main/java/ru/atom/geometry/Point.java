package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider {
    int xCoord;
    int yCoord;

    public Point(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
    // and methods

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
        if (point.xCoord == this.xCoord && point.yCoord ==  this.yCoord) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point pointTwo = (Point) other;
        if (pointTwo.xCoord == this.xCoord && pointTwo.yCoord == this.yCoord) {
            return true;
        } else {
            return false;
        }
    }
}
