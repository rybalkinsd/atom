package gs.geometry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gs.model.GameObject;

import java.util.ArrayList;

public class Point implements Collider {
    private final int x;
    private final int y;

    @JsonCreator
    public Point(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public static ArrayList<ArrayList<Point>> getExplosions(Point point, int range) {
        ArrayList<Point> explosionsRight = new ArrayList<>();
        ArrayList<Point> explosionsLeft = new ArrayList<>();
        ArrayList<Point> explosionsUp = new ArrayList<>();
        ArrayList<Point> explosionsDown = new ArrayList<>();
        ArrayList<Point> explosionsCurrent = new ArrayList<>();
        explosionsCurrent.add(point);

        ArrayList<ArrayList<Point>> allExplosions = new ArrayList<>();
        for (int i = 1; i < range + 1; i++) {
            explosionsUp.add(new Point(point.getX(), point.getY() + GameObject.getHeightBox() * i));
            explosionsDown.add(new Point(point.getX(), point.getY() - GameObject.getHeightBox() * i));
            explosionsRight.add(new Point(point.getX()  + GameObject.getWidthBox() * i, point.getY()));
            explosionsLeft.add(new Point(point.getX() - GameObject.getWidthBox() * i, point.getY()));
        }
        allExplosions.add(explosionsRight);
        allExplosions.add(explosionsLeft);
        allExplosions.add(explosionsUp);
        allExplosions.add(explosionsDown);
        allExplosions.add(explosionsCurrent);

        return allExplosions;
    }

    public static Point getUp1Position(Point point) {
        return new Point(point.getX(), point.getY() + GameObject.getHeightBox());
    }

    public static Point getUp2Position(Point point) {
        return new Point(point.getX(), point.getY() + GameObject.getHeightBox() * 2);
    }

    public static Point getDown1Position(Point point) {
        return new Point(point.getX(), point.getY() - GameObject.getHeightBox());
    }

    public static Point getDown2Position(Point point) {
        return new Point(point.getX(), point.getY() - GameObject.getHeightBox() * 2);
    }

    public static Point getRight1Position(Point point) {
        return new Point(point.getX() + GameObject.getWidthBox(), point.getY());
    }

    public static Point getRight2Position(Point point) {
        return new Point(point.getX() + GameObject.getWidthBox() * 2, point.getY());
    }

    public static Point getLeft1Position(Point point) {
        return new Point(point.getX() - GameObject.getWidthBox(), point.getY());
    }

    public static Point getLeft2Position(Point point) {
        return new Point(point.getX() - GameObject.getWidthBox() * 2, point.getY());
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

    public Point getRightPoint(int i) {
        return new Point(x + 32 * i, y);
    }

    public Point getLeftPoint(int i) {
        return new Point(x - 32 * i, y);
    }

    public Point getUpperPoint(int i) {
        return new Point(x, y + 32 * i);
    }

    public Point getLowerPoint(int i) {
        return new Point(x, y - 32 * i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x == point.x && y == point.y) {
            return true;
        }

        return false;
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
