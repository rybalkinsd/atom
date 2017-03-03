package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
<<<<<<< HEAD
public class Point /* super class and interfaces here if necessary */ {
=======
public class Point implements Collider /* super class and interfaces here if necessary */ {
>>>>>>> 803adbb... 'FixedFor1Task'
    // fields
    // and methods

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Point point = (Point) o;

        // your code here
<<<<<<< HEAD
        throw new NotImplementedException();
=======
        return (this.getX() == point.getX() && this.getY() == point.getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        return (this.equals(other));
>>>>>>> 803adbb... 'FixedFor1Task'
    }
}
