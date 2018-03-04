package ru.atom.geometry;

public class Bar implements Collider {

    private Point bottomCorner;
    private Point upperCorner;

    public Point getBottomCorner() {
        return bottomCorner;
    }

    public Point getUpperCorner() {
        return upperCorner;
    }


    Bar(final int firstCornerX, final int firstCornerY, final int secondCornerX, final int secondCornerY) {
        //here we made two corners(bot and top) bar, using two opposite bar corner points
        bottomCorner = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY,secondCornerY));
        upperCorner = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY,secondCornerY));
    }


    @Override
    public boolean isColliding(Collider obj) {
        if (obj.getClass() == this.getClass())  {
            Bar bar = (Bar) obj;

            /* it works but it's a little bit slower

            int result1 = Math.min(b.upperCorner.getX(), upperCorner.getX()) -
                          Math.max(b.bottomCorner.getX(), bottomCorner.getX());
            int result2 = Math.min(b.upperCorner.getY(), upperCorner.getY()) -
                          Math.max(b.bottomCorner.getY(), bottomCorner.getY());

            if(result1 >= 0 &&  result2 >= 0)
                return true;
            else
                return false;
            */

            boolean thereIsNoIntersectX = (bar.bottomCorner.getX() > upperCorner.getX())
                                       || (bottomCorner.getX() > bar.upperCorner.getX());

            boolean thereIsNoIntersectY = (bar.bottomCorner.getY() > upperCorner.getY())
                                       || (bottomCorner.getY() > bar.upperCorner.getY());

            if (!(thereIsNoIntersectX || thereIsNoIntersectY))
                return true;
            else
                return false;
        }

        if (obj instanceof  Point) {
            Point point = (Point) obj;
            if (point.getX() >= bottomCorner.getX() && point.getX() <= upperCorner.getX()
                && point.getY() >= bottomCorner.getY() && point.getY() <= upperCorner.getY())
                return true;
            else
                return false;
        }

        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) obj;


        if (bottomCorner.equals(bar.getBottomCorner()) && upperCorner.equals(bar.getUpperCorner()))
            return true;

        return false;
    }
}
