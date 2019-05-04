package ru.atom.geometry;

public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;

    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Bar bar = (Bar) obj;
        return this.firstCornerX == bar.getFirstCornerX()
                && this.secondCornerX == bar.getSecondCornerX()
                && this.firstCornerY == bar.getFirstCornerY()
                && this.secondCornerY == bar.getSecondCornerY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;

            return point.getX() >= this.firstCornerX
                    && point.getX() <= this.secondCornerX
                    && point.getY() >= this.firstCornerY
                    && point.getY() <= this.secondCornerY;
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;

            if (this.firstCornerX > bar.getSecondCornerX()
                    || this.secondCornerX < bar.getFirstCornerX()
                    || this.firstCornerY > bar.getSecondCornerY()
                    || this.secondCornerY < bar.getFirstCornerY()) return false;

            return true;
        }
        return false;
    }
}
