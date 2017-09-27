package ru.atom.geometry;

public class Bar implements Collider {
    private Point point1;
    private Point point2;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.point1 = new Point(firstCornerX, firstCornerY);
        this.point2 = new Point(secondCornerX, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {

        if (other instanceof Bar) {
            if ((this.isColliding(((Bar) other).point1)
                    || this.isColliding(((Bar) other).point2))) return true;
        }

        if (other instanceof Point) {
            int pointX = ((Point) other).getX();
            int pointY = ((Point) other).getY();
            if (pointX >= point1.getX() && pointX <= point2.getX()
                    && pointY >= point1.getY() && pointY <= point2.getY()) return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return (point1.equals(((Bar) o).point1) && point2.equals(((Bar) o).point2));
    }
}
