package ru.atom.geometry;

public final class Utils {
    public static boolean barIsCollidingWithPoint(Bar bar, Point point) {
        return bar.getFirstCornerX() <= point.getX()
                && bar.getFirstCornerY() <= point.getY()
                && bar.getSecondCornerX() >= point.getX()
                && bar.getSecondCornerY() >= point.getY();
    }
}
