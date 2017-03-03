package ru.atom.geometry;

/**
 * Created by Vlad on 03.03.2017.
 */
public class Bar implements Collider {
    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getLowerLeftX() {
        return Math.min(this.firstPointX, this.secondCornerX);
    }
    public int getLowerLeftY() {
        return Math.min(this.firstCornerY, this.secondCornerY);
    }

    public int getUpperRightX() {
        return Math.max(this.firstPointX, this.secondCornerX);
    }
    public int getUpperRightY() {
        return Math.max(this.firstCornerY, this.secondCornerY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        return bar.getLowerLeftY() == this.getLowerLeftX() &&
                bar.getLowerLeftY() == this.getLowerLeftY() &&
                bar.getUpperRightX() == this.getUpperRightY() &&
                bar.getUpperRightY() == this.getUpperRightY();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return this.equals(other) || !(((Bar) other).getLowerLeftX() > this.getUpperRightX() ||
                            ((Bar) other).getLowerLeftY() > this.getUpperRightY() ||
                            this.getLowerLeftX() > ((Bar) other).getUpperRightX() ||
                            this.getLowerLeftY() > ((Bar) other).getUpperRightY() );

        }

        if (other instanceof Point) {
            return other.isColliding(this);
        }
        return false;
    }
}
