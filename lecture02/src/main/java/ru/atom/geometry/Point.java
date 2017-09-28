package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {
    // fields
    // and methods

    private final int x ;
    private final int y ;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }
    /*
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.public Point(int x, int y) {
    this.x = x;
    this.y = y;
    }
     */
    
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Point point = (Point) o;


        if (this.x == point.x && this.y == point.y) {

            return true;

        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {

        return (this.equals(other));

    }

}