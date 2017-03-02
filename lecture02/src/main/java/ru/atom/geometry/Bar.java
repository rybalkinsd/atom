package ru.atom.geometry;

/**
 * Created by Fella on 01.03.2017.
 */
public class Bar implements Collider {
    Point point1;
    Point point2;
    Point point3;
    Point point4;




    Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.point1 = new Point(Integer.max(firstPointX, secondCornerX), Integer.max(firstCornerY, secondCornerY));
        this.point2 = new Point(Integer.min(firstPointX, secondCornerX),Integer.min(firstCornerY,secondCornerY));
        this.point3 = new Point(this.point1.x, this.point2.y);
        this.point4 = new Point(this.point2.x, this.point1.y);

    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        if (this.point1.equals(bar.point1) && this.point2.equals(bar.point2)) return true;
        else return false;
    }







    public boolean pointInIntervalX(Point point) {
        if (this.point1.x >= point.x && this.point2.x <= point.x) return true;
        else return false;
    }


    public boolean pointInIntervalY(Point point) {
        if (this.point1.y >= point.y && this.point2.y <= point.y) return true;
        else return false;
    }






    /* public boolean barInInterval( Bar bar){
        if(this.pointInIntervalX(bar.point1)&& this.pointInIntervalY(bar.point2)) return true;
        else return false;
    }*/






    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) return true;
        else if (other == null || getClass() != other.getClass() && other.getClass() != Point.class) return false;
        else if (getClass() == other.getClass()) {
            Bar bar = (Bar) other;
            if ((this.pointInIntervalX(bar.point1) && this.pointInIntervalY(bar.point2))
                    || (this.pointInIntervalX(bar.point2) && this.pointInIntervalY(bar.point1))
                    || (this.pointInIntervalX(bar.point3) && this.pointInIntervalY(bar.point4))
                    || (this.pointInIntervalX(bar.point4) && this.pointInIntervalY(bar.point3))) return true;
        } else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            if (this.pointInIntervalX(point) && this.pointInIntervalY(point)) return true;
        } else return false;

        return false;
    }
}
