package ru.atom.geometry;


import java.util.ArrayList;

import java.util.List;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX,  int firstCornerY,
               int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public void setFirstCornerX(int firstCornerX) {
        this.firstCornerX = firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public void setFirstCornerY(int firstCornerY) {
        this.firstCornerY = firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public void setSecondCornerX(int secondCornerX) {
        this.secondCornerX = secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    protected void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            if (this.equals(other)) {
                return true;
            } else if (barIncludesBar(this, (Bar) other)
                    || (barIncludesBar((Bar) other, this))) {
                return true;
            } else {


                List<Point> firstBar = getPointsList(this);
                List<Point> secondBar = getPointsList((Bar) other);
                for (int i = 0; i < secondBar.size() - 1; i++) {
                    for (int j = 0; j < firstBar.size() - 1; j++) {
                        if (doIntersect(secondBar.get(i), secondBar.get(i + 1),
                                firstBar.get(j), firstBar.get(j + 1))) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } else {
            if (other instanceof Point) {
                return ((Point) other).getX() >= this.firstCornerX
                        && ((Point) other).getX() <= this.secondCornerX
                        && ((Point) other).getY() >= this.firstCornerY
                        && ((Point) other).getY() <= this.secondCornerY;

            }
        }
        return false;
    }

    private static List<Point> getPointsList(Bar bar) {
        Point point1 = new Point(bar.getFirstCornerX(), bar.getFirstCornerY());
        Point point2 = new Point(bar.getSecondCornerX(), bar.getSecondCornerY());
        List<Point> allPoint = new ArrayList<>();
        allPoint.add(point1);
        allPoint.add(new Point(point1.getX(), point2.getY()));
        allPoint.add(point2);
        allPoint.add(new Point(point2.getX(), point1.getY()));
        return allPoint;
    }

    /*
     * checks if point q lies on line segment 'pr'
     * */
    private static boolean onSegment(Point p, Point q, Point r) {
        if (q.getX() <= Math.max(p.getX(), r.getX())
                && q.getX() >= Math.min(p.getX(), r.getX())
                && q.getY() <= Math.max(p.getY(), r.getY())
                && q.getY() >= Math.min(p.getY(), r.getY())
                ) {
            return true;
        }
        return false;
    }

    /*
     * To find orientation of ordered triplet (p, q, r).
     * See more https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
     * The function returns following values
     * 0 --> p, q and r are colinear
     * 1 --> Clockwise
     * 2 --> Counterclockwise
     * */
    private static int orientation(Point p, Point q, Point r) {
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) return 0;

        return (val > 0) ? 1 : 2;

    }

    private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }

    public static boolean barIncludesBar(Bar bar1, Bar bar2) {
        if (bar1.getFirstCornerX()
                < Math.min(bar2.getFirstCornerX(), bar2.getSecondCornerX())
                && bar1.getSecondCornerX()
                > Math.max(bar2.getFirstCornerX(), bar2.getSecondCornerX())
                && bar1.getFirstCornerY()
                < Math.min(bar2.getFirstCornerY(), bar2.getSecondCornerY())
                && bar1.getSecondCornerY()
                > Math.min(bar2.getFirstCornerY(), bar2.getSecondCornerY())
                ) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return (this.firstCornerX == bar.firstCornerX
                || this.firstCornerX == bar.secondCornerX)
                && (this.firstCornerY == bar.firstCornerY
                || this.firstCornerY == bar.secondCornerY)
                && (this.secondCornerX == bar.secondCornerX
                || this.secondCornerX == bar.firstCornerX)
                && (this.secondCornerY == bar.secondCornerY
                || this.secondCornerY == bar.firstCornerY);
    }
}
