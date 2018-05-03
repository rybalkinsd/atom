package ru.atom.geometry;

import java.util.Objects;

public class Bar implements Collider {
    private Point topLeft;
    private Point topRight;
    private Point bottomLeft;
    private Point bottomRight;


    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstCornerX < secondCornerX && firstCornerY < secondCornerY) {
            bottomLeft = new Point(firstCornerX, firstCornerY);
            topRight = new Point(secondCornerX, secondCornerY);
            topLeft = new Point(firstCornerX, secondCornerY);
            bottomRight = new Point(secondCornerX, firstCornerY);
        } else if (firstCornerX > secondCornerX && firstCornerY > secondCornerY) {
            topRight = new Point(firstCornerX, firstCornerY);
            bottomLeft = new Point(secondCornerX, secondCornerY);
            bottomRight = new Point(firstCornerX, secondCornerY);
            topLeft = new Point(secondCornerX, firstCornerY);
        } else if (firstCornerX < secondCornerX && firstCornerY > secondCornerY) {
            topLeft = new Point(firstCornerX, firstCornerY);
            bottomRight = new Point(secondCornerX, secondCornerY);
            topRight = new Point(secondCornerX, firstCornerY);
            bottomLeft = new Point(firstCornerX, secondCornerY);
        } else if (firstCornerX > secondCornerX && firstCornerY < secondCornerY) {
            bottomRight = new Point(firstCornerX, firstCornerY);
            topLeft = new Point(secondCornerX, secondCornerY);
            bottomLeft = new Point(secondCornerX, firstCornerY);
            topRight = new Point(firstCornerX, secondCornerY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bar)) return false;
        Bar bar = (Bar) o;
        return Objects.equals(topLeft, bar.topLeft)
                && Objects.equals(topRight, bar.topRight)
                && Objects.equals(bottomLeft, bar.bottomLeft)
                && Objects.equals(bottomRight, bar.bottomRight);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return ((Point) other).getX() >= this.bottomLeft.getX()
                    && ((Point) other).getX() <= this.bottomRight.getX()
                    && ((Point) other).getY() >= this.bottomLeft.getY()
                    && ((Point) other).getY() <= this.topLeft.getY();
        } else if (other instanceof Bar) {
            return (this.bottomLeft.getX() <= ((Bar) other).bottomLeft.getX()
                    || this.bottomLeft.getX() <= ((Bar) other).bottomRight.getX())
                    && (this.bottomRight.getX() >= ((Bar) other).bottomLeft.getX()
                    || this.bottomRight.getX() >= ((Bar) other).bottomRight.getX())
                    && (this.bottomLeft.getY() <= ((Bar) other).bottomLeft.getY()
                    || this.bottomLeft.getY() <= ((Bar) other).topLeft.getY())
                    && (this.topLeft.getY() >= ((Bar) other).bottomLeft.getY()
                    || this.topLeft.getY() >= ((Bar) other).topLeft.getY());
        } else
            return false;
    }
}