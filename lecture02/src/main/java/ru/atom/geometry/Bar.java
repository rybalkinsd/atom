package ru.atom.geometry;


import static java.lang.Math.max;
import static java.lang.Math.min;


public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;


    /*    public int getFirstCornerX() {
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
    */


    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = min(firstCornerX, secondCornerX);   //X of left bot
        this.firstCornerY = min(firstCornerY, secondCornerY);   //Y of left bot
        this.secondCornerX = max(firstCornerX, secondCornerX);  //X of right top
        this.secondCornerY = max(firstCornerY, secondCornerY);  //Y of right top
    }

    @Override
    public boolean isColliding(Collider other) {


        if (other instanceof Point) {
            // Point processing
            //System.out.println("here");
            if (this == other) return true;

            Point point = (Point) other;
            return  !(point.getX() < this.firstCornerX
                    || point.getY() < this.firstCornerY
                    || point.getX() > this.secondCornerX
                    || point.getY() > this.secondCornerY);

        } else if (other instanceof Bar) {

            // Bar processing

            if (this == other) return true;

            Bar bar = (Bar) other;

            return !(bar.secondCornerX < this.firstCornerX
                    || bar.secondCornerY < this.firstCornerY
                    || bar.firstCornerX > this.secondCornerX
                    || bar.firstCornerY > this.secondCornerY);

        }
        throw new IllegalArgumentException();

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return !(bar.firstCornerX != this.firstCornerX
                 || bar.firstCornerY != this.firstCornerY
                 || bar.secondCornerX != this.secondCornerX
                 || bar.secondCornerY != this.secondCornerY);

    }

}
