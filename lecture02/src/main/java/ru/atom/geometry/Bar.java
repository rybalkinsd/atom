package ru.atom.geometry;

/**
 * Template class for
 *
 *  ------------o rightUpCorner
 * |            |
 * |            |
 * |    BAR     |
 * |            |
 * |            |
 * o------------
 * leftDownCorner
 *
 *
 */
public class Bar implements Collider/* super class and interfaces here if necessary */ {
    // fields
    private int leftDownCornerX;
    private int leftDownCornerY;
    private int rightUpCornerX;
    private int rightUpCornerY;

    // and methods
    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {


        if (firstCornerY >= secondCornerY) {
            this.leftDownCornerY = secondCornerY;
            this.rightUpCornerY = firstCornerY;
        } else {
            this.leftDownCornerY = firstCornerY;
            this.rightUpCornerY = secondCornerY;
        }

        if (firstCornerX >= secondCornerX) {
            this.rightUpCornerX = firstCornerX;
            this.leftDownCornerX = secondCornerX;
        } else {
            this.rightUpCornerX = secondCornerX;
            this.leftDownCornerX = firstCornerX;
        }

    }

    @Override
    public int getLeftDownCornerX() {
        return leftDownCornerX;
    }

    @Override
    public int getLeftDownCornerY() {
        return this.leftDownCornerY;
    }

    @Override
    public int getRightUpCornerX() {
        return this.rightUpCornerX;
    }

    @Override
    public int getRightUpCornerY() {
        return this.rightUpCornerY;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two bars are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        // your code here
        return (this.leftDownCornerX == bar.leftDownCornerX
                && this.leftDownCornerY == bar.leftDownCornerY
                && this.rightUpCornerX == bar.rightUpCornerX
                && this.rightUpCornerY == bar.rightUpCornerY);
        //throw new UnsupportedOperationException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            if (this.leftDownCornerX <= other.getLeftDownCornerX()
                    && this.rightUpCornerX >= other.getLeftDownCornerX()
                    && this.leftDownCornerY <= other.getLeftDownCornerY()
                    && this.rightUpCornerY >= other.getLeftDownCornerY())
                return true;
            else
                return false;
        } else if (other instanceof Bar) {
            if ((((this.rightUpCornerY >= other.getLeftDownCornerY())
                    && (this.leftDownCornerY <= other.getLeftDownCornerY()))
                    || ((this.rightUpCornerY >= other.getRightUpCornerY())
                    && (this.leftDownCornerY <= other.getRightUpCornerY())))
                    && (((this.leftDownCornerX <= other.getLeftDownCornerX())
                    && (this.rightUpCornerX >= other.getLeftDownCornerX()))
                    || ((this.leftDownCornerX <= other.getRightUpCornerX())
                    && (this.rightUpCornerX >= other.getRightUpCornerX()))))
                return true;
            else if (this.leftDownCornerX >= other.getLeftDownCornerX()
                    && this.leftDownCornerY >= other.getLeftDownCornerY()
                    && this.rightUpCornerX <= other.getRightUpCornerX()
                    && this.rightUpCornerY <= other.getRightUpCornerY())
                return true;
            else
                return false;
        } else
            return false;
    }
}
