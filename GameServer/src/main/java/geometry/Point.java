package geometry;

import objects.GameObject;

public class Point implements Collider {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Point getUp1Position(Point point) {
        return new Point(point.getX(), point.getY() + GameObject.height);
    }

    public static Point getUp2Position(Point point) {
        return new Point(point.getX(), point.getY() + GameObject.height * 2);
    }

    public static Point getDown1Position(Point point) {
        return new Point(point.getX(),point.getY() - GameObject.height);
    }

    public static Point getDown2Position(Point point) {
        return new Point(point.getX(),point.getY() - GameObject.height * 2);
    }

    public static Point getRight1Position(Point point) {
        return new Point(point.getX() + GameObject.width, point.getY());
    }

    public static Point getRight2Position(Point point) {
        return new Point(point.getX() + GameObject.width * 2, point.getY());
    }

    public static Point getLeft1Position(Point point) {
        return new Point(point.getX() - GameObject.width, point.getY());
    }

    public static Point getLeft2Position(Point point) {
        return new Point(point.getX() - GameObject.width * 2, point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        return false;
    }
}
