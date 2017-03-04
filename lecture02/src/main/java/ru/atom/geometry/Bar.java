package ru.atom.geometry;

/**
 * Created by ikozin on 01.03.17.
 */
public class Bar implements Collider {
    private Point first;
    private Point second;

    public Bar(int firstX, int firstY, int secondX, int secondY) {
        first = (Point) Geometry.createPoint(firstX, firstY);
        second = (Point) Geometry.createPoint(secondX, secondY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if ((this.first.equals(bar.first) && this.second.equals(bar.second))
                || (this.first.equals(bar.second) && this.second.equals(bar.first))
                || (this.first.getCoordinates()[1] == bar.first.getCoordinates()[1]
                    && this.first.getCoordinates()[0] == bar.second.getCoordinates()[0])
                || (this.first.getCoordinates()[0] == bar.first.getCoordinates()[0]
                    && this.first.getCoordinates()[1] == bar.second.getCoordinates()[1])) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) { //equal bars collide
            return true;
        } else if (other instanceof Bar
                && (this.first.getCoordinates()[0] <= ((Bar) other).second.getCoordinates()[0]
                    && this.first.getCoordinates()[1] <= ((Bar) other).second.getCoordinates()[1]
                    && this.second.getCoordinates()[0] >= ((Bar) other).first.getCoordinates()[0]
                    && this.second.getCoordinates()[1] >= ((Bar) other).first.getCoordinates()[1]
                ||  ((Bar) other).first.getCoordinates()[0] <= this.second.getCoordinates()[0]
                    && ((Bar) other).first.getCoordinates()[1] <= this.second.getCoordinates()[1]
                    && ((Bar) other).second.getCoordinates()[0] >= this.first.getCoordinates()[0]
                    && ((Bar) other).second.getCoordinates()[1] >= this.first.getCoordinates()[1])) {
            return true;
        } else if (other instanceof Point
                && this.first.getCoordinates()[0] <= other.getCoordinates()[0]
                && this.second.getCoordinates()[0] >= other.getCoordinates()[0]
                && this.first.getCoordinates()[1] <= other.getCoordinates()[1]
                && this.second.getCoordinates()[1] >= other.getCoordinates()[1]) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int[] getCoordinates() {
        int[] coords = {first.getCoordinates()[0], first.getCoordinates()[1],
                second.getCoordinates()[0], second.getCoordinates()[1]};
        return coords;
    }
}
