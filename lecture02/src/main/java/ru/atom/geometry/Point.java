package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private int x;
    private int y;

    Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean isColliding(Collider obj) {
        if (obj.getClass() == this.getClass())  {
            //if obj is a Point and we try check Colliding between two points
            return this.equals(obj);
        } else {
            if (obj instanceof  Bar) {
                //if obj is a Bar and we try check Colliding between point and Bar
                return obj.isColliding(this);
            } else {
                //if obj isn't a Bar , and isn't a Point
                return false;
            }
        }

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

        // if it's the Point with sinillar coord than equals
        if (this.x == point.getX() && this.y == point.getY()) return true;

        return false;


        //throw new UnsupportedOperationException();
    }
}
