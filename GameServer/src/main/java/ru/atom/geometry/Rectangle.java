package ru.atom.geometry;

public class Rectangle implements GeomObject {
    private final Point leftTopCorner;
    private final int width;
    private final int height;

    public Rectangle(Point leftTopCorner, int width, int height) {
        this.leftTopCorner = leftTopCorner;
        this.width = width;
        this.height = height;
    }


    public Point getPosition() {
        return this.leftTopCorner;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public IntersectionParams getIntersection(Rectangle rectangle) {

        Rectangle extBar = new Rectangle(
                new Point(rectangle.getPosition().getX() - width,
                        rectangle.getPosition().getY() - height
                ),
                rectangle.getWidth() + width,
                rectangle.getHeight() + height
        );

        float dxl = extBar.leftTopCorner.getX() - this.leftTopCorner.getX();
        float dxr = extBar.leftTopCorner.getX() + extBar.getWidth() - this.leftTopCorner.getX();
        float dyt = extBar.leftTopCorner.getY() - this.leftTopCorner.getY();
        float dyd = extBar.leftTopCorner.getY() + extBar.getHeight() - this.leftTopCorner.getY();
        float dx = 0;
        float dy = 0;

        if (-dxl > dxr) {
            dx = dxr;
        } else {
            dx = dxl;
        }

        if (-dyt > dyd) {
            dy = dyd;
        } else {
            dy = dyt;
        }


        if (dxl > 0 || dxr < 0) {
            dx = 0;
        }

        if (dyt > 0 || dyd < 0) {
            dy = 0;
        }

        return new IntersectionParams(dx, dy);
    }


    public boolean isColliding(Collider other) {

        if (other.getClass() == Rectangle.class) {
            Rectangle rectangle = (Rectangle)other;
            Rectangle extBar = new Rectangle(
                    new Point(rectangle.getPosition().getX() - width,
                        rectangle.getPosition().getY() - height
                    ),
                    rectangle.getWidth() + width,
                    rectangle.getHeight() + height
            );

            return extBar.isColliding(this.leftTopCorner);


        } else if (other.getClass() == Point.class) {

            Point point = (Point)other;
            return (this.leftTopCorner.getX() < point.getX()
                    && point.getX() < this.leftTopCorner.getX() + this.width
                    && this.leftTopCorner.getY() < point.getY()
                    && point.getY() < this.leftTopCorner.getY() + this.height);
        } else
            return false;
    }


    @Override
    public String toString() {
        return leftTopCorner.toString();
    }
}
