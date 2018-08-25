package ru.atom.geometry;

import java.util.Arrays;
import java.util.List;

public class Bar implements Collider {
    private Point x1;
    private Point x2;

    public Bar(Point x1, Point x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bar)) return false;
        Bar bar = (Bar) o;
        return equalsBarsWithChangeOrientation(this, bar);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return intersects(this, bar);
        } else if (other instanceof Point) {
            Point point = (Point) other;
            return Point.pointInBar(point, this);
        }
        return false;
    }

    public static boolean equalsBarsWithPermutations(Bar barOne, Bar barTwo) {
        return barOne.x1.equals(barTwo.x1) && barOne.x2.equals(barTwo.x2)
                || barOne.x1.equals(barTwo.x2) && barOne.x2.equals(barTwo.x1);
    }

    private static boolean equalsBarsWithChangeOrientation(Bar barOne, Bar barTwo) {
        if (equalsBarsWithPermutations(barOne, barTwo)) {
            return true;
        }
        int diffX = Math.abs(barOne.x1.getX() - barOne.x2.getX());
        int diffY = Math.abs(barOne.x1.getY() - barOne.x2.getY());
        Point point = barOne.x1;
        List<Point> pointList = Arrays.asList(barOne.x1, barOne.x2, barTwo.x1, barTwo.x2);
        Point minPoint = barOne.x1; // down left
        Point maxPoint = barOne.x1; // up right
        for (Point pointIt : pointList) {
            if (pointIt.getX() >= maxPoint.getX() && pointIt.getY() >= maxPoint.getY()) {
                maxPoint = pointIt;
            }
            if (pointIt.getX() <= minPoint.getX() && pointIt.getY() <= minPoint.getY()) {
                minPoint = pointIt;
            }
        }
        Point upLeftPoint = new Point(minPoint.getX(), minPoint.getY() + diffY);
        Point downRightPoint = new Point(minPoint.getX() + diffX, minPoint.getY());
        Bar barTmp = null;
        if (point.equals(maxPoint)) {
            barTmp = new Bar(new Point(point.getX(), point.getY() - diffY),
                    new Point(minPoint.getX(), minPoint.getY() + diffY));
        } else if (point.equals(minPoint)) {
            barTmp = new Bar(new Point(point.getX(), point.getY() + diffY),
                    new Point(maxPoint.getX(), maxPoint.getY() - diffY));
        } else if (point.equals(upLeftPoint)) {
            barTmp = new Bar(new Point(point.getX() + diffX, point.getY()),
                    new Point(downRightPoint.getX() - diffX, downRightPoint.getY()));
        } else if (point.equals(downRightPoint)) {
            barTmp = new Bar(new Point(point.getX() - diffX, point.getY()),
                    new Point(upLeftPoint.getX() + diffX, upLeftPoint.getY()));
        }
        if (barTmp != null) {
            return equalsBarsWithPermutations(barTmp, barTwo);
        } else {
            return false;
        }
    }

    public static boolean intersects(Bar barOne, Bar barTwo) {
        if (barOne.equals(barTwo)) return true;
        return barOne.getX2().getY() >= barTwo.getX1().getY() && barOne.getX1().getY() <= barTwo.getX2().getY()
                && barOne.getX2().getX() >= barTwo.getX1().getX() && barOne.getX1().getX() <= barTwo.getX2().getX();
    }

    public Point getX1() {
        return x1;
    }

    public Point getX2() {
        return x2;
    }
}
