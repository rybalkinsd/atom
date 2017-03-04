package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Fella on 01.03.2017.
 */
public class Bar implements Collider {
    private final Point point1;
    private final Point point2;




    Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.point1 = new Point(Integer.max(firstPointX, secondCornerX), Integer.max(firstCornerY, secondCornerY));
        this.point2 = new Point(Integer.min(firstPointX, secondCornerX),Integer.min(firstCornerY,secondCornerY));

    }


    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Bar bar = (Bar) o;
        if (this.point1.equals(bar.point1) && this.point2.equals(bar.point2)) return true;
        else return false;
    }







    private final boolean pointInIntervalX(Point point) {
        if (this.point1.getX() >= point.getX() && this.point2.getX() <= point.getX()) return true;
        else return false;
    }


    private final boolean pointInIntervalY(Point point) {
        if (this.point1.getY() >= point.getY() && this.point2.getY() <= point.getY()) return true;
        else return false;
    }


    private final boolean pointIntoBar(Point point) {
        if (this.pointInIntervalX(point) && this.pointInIntervalY(point)) return true;
        else return false;
    }



    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) return true;
        else if (other == null || getClass() != other.getClass() && other.getClass() != Point.class) return false;
        else if (getClass() == other.getClass()) {
            Bar bar = (Bar) other;
            if (this.pointIntoBar(bar.getPoint1()) || this.pointIntoBar(bar.getPoint2())
                    || bar.pointIntoBar(this.point1) || bar.pointIntoBar(this.point2)) return true;
            else return false;
        } else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            if (this.pointInIntervalX(point) && this.pointInIntervalY(point)) return true;
            else return false;
        }
        throw new NotImplementedException();
    }

}
