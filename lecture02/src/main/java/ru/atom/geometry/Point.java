package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point /* super class and interfaces here if necessary */ implements Collider {
    // fields
    // and methods
    private int x, y;
    public Point(int x, int y) { //constructor
        this.x = x;
        this.y = y;
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
        if ((this.x == point.x) && (this.y == point.y)){
            return true;
        }
        // your code here
        return false;
    }
    @Override //переопределение коллайдера
    public boolean isColliding(Collider other) {
        if (this == other){
            return true;
        }else {
            if (this.equals(other)){
                return true;
            }
        }
        return false;
    }
}
