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

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Rectangle(" + leftTopCorner.toString() + " , width = " + width + ", height =" + height + ")";
    }
}
