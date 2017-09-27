package ru.atom.geometry;

public class IntersectionDetector {

    public static boolean isIntersects(Bar bar, Point point) {
        return point.getX() <= bar.getStartCorner().getX() + bar.getWidth()
                && point.getY() <= bar.getStartCorner().getY() + bar.getHeight();
    }

    public static boolean isIntersects(Bar bar1, Bar bar2) {
        int width = bar1.getStartCorner().getX() < bar2.getStartCorner().getX() ? bar1.getWidth() : bar2.getWidth();
        int height = bar1.getStartCorner().getY() < bar2.getStartCorner().getY() ? bar1.getHeight() : bar2.getHeight();
        return Math.abs(bar1.getStartCorner().getX()  - bar2.getStartCorner().getX()) <= width
                && Math.abs(bar1.getStartCorner().getY() - bar2.getStartCorner().getY()) <= height;
    }

    public static boolean isIntersects(Point point1, Point point2) {
        return point1.equals(point2);
    }
}
