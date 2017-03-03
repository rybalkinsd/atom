package ru.atom.geometry;

import ru.atom.geometry.Collider;

/**
 * Created by vladfedorenko on 01.03.17.
 */
public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstCornerX <= secondCornerX) {
            this.firstCornerX = firstCornerX;
            this.secondCornerX = secondCornerX;
        } else {
            this.firstCornerX = secondCornerX;
            this.secondCornerX = firstCornerX;
        }
        if (firstCornerY <= secondCornerY) {
            this.firstCornerY = firstCornerY;
            this.secondCornerY = secondCornerY;
        } else {
            this.firstCornerY = secondCornerY;
            this.secondCornerY = firstCornerY;
        }
    }

    public int getFirstCornerX() {
        return this.firstCornerX;
    }

    public int getFirstCornerY() {
        return this.firstCornerY;
    }

    public int getSecondCornerX() {
        return this.secondCornerX;
    }

    public int getSecondCornerY() {
        return this.secondCornerY;
    }

    public boolean isColliding(Collider o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Collider)) return false;

        if (o instanceof Point) {
            Point point = (Point) o;
            return (this.getFirstCornerX() <= point.getX()
                    && point.getX() <= this.getSecondCornerX()
                    && this.getFirstCornerY() <= point.getY()
                    && point.getY() <= this.getSecondCornerY());
        } else if (o instanceof Bar) {
            Bar bar = (Bar) o;
            return (this.getFirstCornerX() <= bar.getFirstCornerX()
                    && bar.getFirstCornerX() <= this.getSecondCornerX()
                    || this.getFirstCornerX() <= bar.getSecondCornerX()
                    && bar.getSecondCornerX() <= this.getSecondCornerX())
                    && (this.getFirstCornerY() <= bar.getFirstCornerY()
                    && bar.getFirstCornerY() <= this.getSecondCornerY()
                    || this.getFirstCornerY() <= bar.getSecondCornerY()
                    && bar.getSecondCornerY() <= this.getSecondCornerY());
        } else return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Bar)) return false;
        Bar bar = (Bar) o;
        return this.getFirstCornerX() == bar.getFirstCornerX()
                && this.getSecondCornerX() == bar.getSecondCornerX()
                && this.getFirstCornerY() == bar.getFirstCornerY()
                && this.getSecondCornerY() == bar.getSecondCornerY();
    }

}
