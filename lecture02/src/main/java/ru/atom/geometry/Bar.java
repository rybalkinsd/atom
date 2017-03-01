package ru.atom.geometry;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by ilysk on 01.03.17.
 */
public class Bar implements Collider {
    protected int firstCornerX;
    protected int firstCornerY;
    protected int secondCornerX;
    protected int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = min(firstCornerX, secondCornerX);
        this.firstCornerY = min(firstCornerY, secondCornerY);
        this.secondCornerX = max(firstCornerX, secondCornerX);
        this.secondCornerY = max(firstCornerY, secondCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return (this.firstCornerY == bar.firstCornerY) && (this.firstCornerX == bar.firstCornerX)
                && (this.secondCornerX == bar.secondCornerX) && (this.secondCornerY == bar.secondCornerY);
    }

    @Override
    public boolean isColliding(Collider o) {
        if (this == o) return true;

        if (o instanceof Point) {

            Point point = (Point) o;

            return point.x >= this.firstCornerX && point.x <= this.secondCornerX
                    && point.y >= this.firstCornerY && point.y <= this.secondCornerY;
        } else {
            Bar bar = (Bar) o;

            if (this.equals(bar)) {
                return true;
            }

            return (this.secondCornerX >= bar.firstCornerX && this.firstCornerY <= bar.secondCornerY)
                    && (bar.secondCornerX >= this.firstCornerX && bar.firstCornerY <= this.secondCornerY)
                    || (this.secondCornerX >= bar.firstCornerX && this.firstCornerY <= bar.secondCornerY)
                    && (bar.secondCornerX >= this.firstCornerX && bar.firstCornerY <= this.secondCornerY);
        }
    }
}
