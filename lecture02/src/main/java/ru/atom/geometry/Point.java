package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider/* super class and interfaces here if necessary */ {

    public int x;
    public int y;
    public Point(int x, int y) {
    this.x = x;
    this.y = y;
    }

    public int getX () {
        return x;
    }
    public int getY () {
        return y;
    }

    public boolean isColliding(Collider other) {

        if (other instanceof Point) {
            if (x == ((Point) other).getX() && y == ((Point) other).getY()) return true;

        }

        return false;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Point point = (Point) o;
        if (x == point.x && y == point.y) return true;
        else return false;


    }
}
