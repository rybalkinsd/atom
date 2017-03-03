package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //public int getX(){
    //    return x;
    //}
    //public int getY(){
    //    return y;
    // }


    // fields
    // and methods

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // cast from Object to Point
        Point point = (Point) o;
        if (x == point.x && y == point.y) {
            return true;
        } else {
            return false;
        }


        // your code here
        //throw new NotImplementedException();
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other.equals(this) == true)
            return true;
        else return false;
    }
}





