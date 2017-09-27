package ru.atom.geometry;

/**
 * Template class for
 */
public class Point implements Collider /* super class and interfaces here if necessary */ {

    public int x;
    public int y;


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    public boolean isColliding(Collider o) {
        if (o instanceof Point) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
        if (o instanceof Bar) {
            Bar bar = (Bar) o;
            boolean x1 = ((x >= bar.firstCornerX) && (x <= bar.secondCornerX));
            boolean y1 = ((y >= bar.firstCornerY) && (y <= bar.secondCornerY));
            return x1 && y1;
        }
        return false;
    }


    public Point(int xcoord, int ycoord) {

        x = xcoord;
        y = ycoord;

    }

}
