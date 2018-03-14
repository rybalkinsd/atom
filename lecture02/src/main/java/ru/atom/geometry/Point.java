package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    private int x;
    private int y;

    // and methods
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getLeftDownCornerX() {
        return this.x;
    }

    @Override
    public int getLeftDownCornerY() {
        return this.y;
    }

    @Override
    public int getRightUpCornerX() {
        return this.x;
    }

    @Override
    public int getRightUpCornerY() {
        return this.y;
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

        // your code here
        return (this.x == point.x && this.y == point.y);
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            if (other.getLeftDownCornerX() <= this.x
                    && other.getRightUpCornerX() >= this.x
                    && other.getLeftDownCornerY() <= this.y
                    && other.getRightUpCornerY() >= this.y)
                return true;
            else
                return false;
        } else if (other instanceof Point) {
            return (this.x == other.getLeftDownCornerX() && this.y == other.getLeftDownCornerY());
        } else
            return false;
    }
}
