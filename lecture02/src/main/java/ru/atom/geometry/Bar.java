package ru.atom.geometry;

public class Bar implements Collider {
    int firstCornerX;
    int firstCornerY;
    int secondCornerX;
    int secondCornerY;

    Bar(int x1, int y1, int x2, int y2) {
        firstCornerX = x1;
        firstCornerY = y1;
        secondCornerX = x2;
        secondCornerY = y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (this.firstCornerX == bar.firstCornerX && this.firstCornerY == bar.firstCornerY
                && this.secondCornerX == bar.secondCornerX && this.secondCornerY == bar.secondCornerY) return true;

        if (this.firstCornerX == bar.secondCornerX && this.firstCornerY == bar.firstCornerY
                 && this.secondCornerX == bar.firstCornerX && this.secondCornerY == bar.secondCornerY) return true;

        if (this.firstCornerX == bar.firstCornerX && this.secondCornerY == bar.firstCornerY
                 && this.secondCornerX == bar.secondCornerX && this.firstCornerY == bar.secondCornerY) return true;

        if (this.firstCornerX == bar.secondCornerX && this.secondCornerY == bar.firstCornerY
                  && this.secondCornerX == bar.firstCornerX && this.firstCornerY == bar.secondCornerY) return true;

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (this.firstCornerX > bar.secondCornerX || this.secondCornerX < bar.firstCornerX
                || this.firstCornerY > bar.secondCornerY || this.secondCornerY < bar.firstCornerY) {
                return false;
            }
            return true;
        }
        if (other instanceof Point) {
            Point point = (Point) other;
            if (this.firstCornerX > point.x || this.secondCornerX < point.x
                    || this.firstCornerY > point.y || this.secondCornerY < point.y) {
                return false;
            }
            return true;
        }
        return false;
    }

}