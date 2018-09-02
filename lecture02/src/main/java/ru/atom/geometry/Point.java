package ru.atom.geometry;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */ {
    // fields
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

        // your code here
        throw new UnsupportedOperationException();
    }
}
