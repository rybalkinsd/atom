package ru.atom.geometry;


/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {
    private final int x;
    private final int y;


    Point(int x, int y) {
        this.x = x;
        this.y = y;
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


        Point point = (Point) o;
        if (this.x == point.x && this.y == point.y) return true;
        else {
            return false;
        }
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }



    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) return true;
        else return false;

    }
}
