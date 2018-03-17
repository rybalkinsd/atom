package ru.atom.geometry;

public class Bar implements Collider {

    private final int firstCornerX;
    private final int secondCornerX;
    private final int secondCornerY;
    private final int firstCornerY;

    Bar(int firstCornerX, int secondCornerX, int firstCornerY, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.secondCornerX = secondCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;

            return this.firstCornerX <= point.getX() && this.secondCornerX >= point.getX()
                    && this.firstCornerY <= point.getY() && this.secondCornerY >= point.getY();
        } else {
            if (other instanceof Bar) {
                Bar bar = (Bar) other;

                if (this.firstCornerX <= bar.getFirstCornerX() && this.getSecondCornerX() >= bar.getSecondCornerX()
                        && this.secondCornerY >= bar.getSecondCornerY() && this.firstCornerY <= bar.getSecondCornerY())
                    return true;


                if (this.firstCornerX <= bar.getFirstCornerX() && this.secondCornerY >= bar.getFirstCornerY()
                        && this.secondCornerX >= bar.getFirstCornerX() && this.firstCornerY <= bar.getFirstCornerY()
                        || this.firstCornerX <= bar.getFirstCornerX() && this.firstCornerY <= bar.getSecondCornerY()
                        && this.secondCornerX >= bar.getFirstCornerX() && this.secondCornerY >= bar.getSecondCornerY()
                        || this.firstCornerX <= bar.getFirstCornerX() && this.secondCornerY >= bar.getFirstCornerY()
                        && this.secondCornerX >= bar.getSecondCornerX() && this.firstCornerY <= bar.getSecondCornerY()
                        || this.firstCornerX <= bar.getSecondCornerX() && this.firstCornerY <= bar.getSecondCornerY()
                        && this.secondCornerX >= bar.getSecondCornerX() && this.secondCornerY >= bar.getSecondCornerY()
                        || this.firstCornerX <= bar.getFirstCornerX() && this.secondCornerX >= bar.getSecondCornerX()
                        && this.firstCornerY >= bar.firstCornerY && this.secondCornerY <= bar.getSecondCornerY())
                    return true;

                if (this.firstCornerX >= bar.getFirstCornerX() && this.firstCornerY <= bar.getFirstCornerY()
                        && this.secondCornerX <= bar.getSecondCornerX() && this.secondCornerY >= bar.getSecondCornerY())
                    return true;


            } else
                return false;

            return false;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else {
            if (obj instanceof Bar) {
                Bar bar = (Bar) obj;
                if (this.firstCornerY == bar.getFirstCornerY() && this.secondCornerY == bar.getSecondCornerY()
                        && this.firstCornerX == bar.getFirstCornerX() && this.secondCornerX == bar.getSecondCornerX())
                    return true;
                if (this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getFirstCornerY()
                        && this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getSecondCornerY())
                    return true;
                if (this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getSecondCornerY()
                        && this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getFirstCornerY())
                    return true;
                if (this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getFirstCornerY()
                        && this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getSecondCornerY())
                    return true;
            }
            return false;
        }
    }
}
