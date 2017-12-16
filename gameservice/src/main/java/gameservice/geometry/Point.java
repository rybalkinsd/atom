package gameservice.geometry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameservice.model.GameObject;

public class Point implements Collider {
    private final int x;
    private final int y;

    @JsonCreator
    public Point(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public static Point getUpPoint(Point point) {
        return new Point(point.getX(), point.getY() + GameObject.getHeightBox());
    }

    public static Point getDownPoint(Point point) {
        return new Point(point.getX(), point.getY() - GameObject.getHeightBox());
    }

    public static Point getRightPoint(Point point) {
        return new Point(point.getX() + GameObject.getWidthBox(), point.getY());
    }

    public static Point getLeftPoint(Point point) {
        return new Point(point.getX() - GameObject.getWidthBox(), point.getY());
    }

    public Point convertToBitmapPosition() {
        return new Point(x / 32, y / 32);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
