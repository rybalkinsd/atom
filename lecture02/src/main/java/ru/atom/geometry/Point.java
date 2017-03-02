package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    // fields
    private int x;
    private int y;

    public Point ( int x, int y ) {
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public void setX ( int x ) {
        this.x = x;
    }
    public void setY ( int y ) {
        this.y = y;
    }

    @Override
    public boolean isColliding ( Collider other ) {
        return this.equals( (Point) other );
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        // cast from Object to Point
        Point point = (Point) o;
        if ( this.getX() == point.getX() && this.getY() == point.getY() ) {
            return true;
        } else {
            return false;
        }


        //throw new NotImplementedException();

    }
}
