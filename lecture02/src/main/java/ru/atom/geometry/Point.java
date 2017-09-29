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


    public boolean isColliding(Collider other) {

        if (other instanceof Point) {
            if (x == ((Point) other).x && y == ((Point) other).y) return true;

        } else {
            Bar bar = (Bar) other;
            if (bar.x1 <= x && x <= bar.x2 && bar.y1 <= y && y <= bar.y2) return true;
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
