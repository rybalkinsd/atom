package ru.atom.geometry;

/**
 * Created by Даша on 01.03.2017.
 */
public class Bar implements Collider{
    //fields
    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    //methods
    public Bar() {
    }

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if (firstPointX < secondCornerX) {
            this.firstPointX=firstPointX;
            this.secondCornerX = secondCornerX;
        }
        else {
            this.secondCornerX=firstPointX;
            this.firstPointX=secondCornerX;
        }
        if (firstCornerY < secondCornerY) {
            this.firstCornerY=firstCornerY;
            this.secondCornerY = secondCornerY;
        }
        else {
            this.secondCornerY=firstCornerY;
            this.firstCornerY=secondCornerY;
        }
    }

    public boolean isColliding(Collider other) {
        if (other.getClass() == getClass()) {
            Bar bar = (Bar) other;
            return ((this.firstPointX <= bar.firstPointX) && (this.secondCornerX >= bar.firstPointX)
                    || (this.firstPointX <= bar.secondCornerX) && (this.secondCornerX >= bar.secondCornerX))
                    && ((this.firstCornerY <= bar.firstCornerY) && (this.secondCornerY >= bar.firstCornerY)
                    || (this.firstCornerY <= bar.secondCornerY) && (this.secondCornerY >= bar.secondCornerY));
        }
        else if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return (this.firstPointX <= point.getX()) && (this.secondCornerX >= point.getX())
                    && (this.firstCornerY <= point.getY()) && (this.secondCornerY >= point.getY());
        }
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        return (this.firstPointX == bar.firstPointX) && (this.firstCornerY == bar.firstCornerY) && (this.secondCornerX == bar.secondCornerX) && (this.secondCornerX == bar.secondCornerX);
    }
}
