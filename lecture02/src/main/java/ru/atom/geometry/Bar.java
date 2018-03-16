package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY,
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

    public void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {

        if (other instanceof Bar)

            if (this.equals(other)) {
                return true;
            } else {
                return this.isPointOnBorderOfBar(new Point(((Bar) other).getFirstCornerX(), ((Bar) other).getFirstCornerY()))
                        ||
                        this.isPointOnBorderOfBar(new Point(((Bar) other).getSecondCornerX(), ((Bar) other).getSecondCornerY()));
            }


        if (other instanceof Point) {
            return this.isPointOnBorderOfBar((Point) other)
                    || new Point(this.getFirstCornerX(), this.getFirstCornerY()).equals(other)
                    || new Point(this.getSecondCornerX(), this.getSecondCornerY()).equals(other);
        }
        return false;
    }

    public boolean isPointOnBorderOfBar(Point point) {
        return point.getX() >= this.getFirstCornerX()
                && point.getX() <= this.getSecondCornerX()
                && point.getY() >= this.getFirstCornerY()
                && point.getY() <= this.getSecondCornerY();
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
