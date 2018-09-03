package ru.atom.geometry;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class Bar implements Collider {
    private int firstCornerX, firstCornerY, secondCornerX, secondCornerY;

    public int getFirstCornerX() {
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

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = min(firstCornerX, secondCornerX);
        this.firstCornerY = max(firstCornerY, secondCornerY);
        this.secondCornerX = max(firstCornerX, secondCornerX);
        this.secondCornerY = min(firstCornerY, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if(getClass() == other.getClass()){
            Bar bar = (Bar) other;

            if ( bar.firstCornerY < this.secondCornerY || bar.secondCornerY > this.firstCornerY ){
                return false;
            }
            if (bar.secondCornerX < this.firstCornerX || bar.firstCornerX > this.secondCornerX ){
                return false;
            }
            return true;
        }
        else{
            Point point = (Point) other;
            return !( this.firstCornerY < point.getY() || this.secondCornerY > point.getY() || this.firstCornerX > point.getX() || this.secondCornerX < point.getX() );
        }
    }
    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Bar bar = (Bar) other;
        double d1 = sqrt((firstCornerX - secondCornerX) * (firstCornerX - secondCornerX) + (firstCornerY - secondCornerY) * (firstCornerY - secondCornerY));
        double d2 = sqrt((bar.firstCornerX - bar.secondCornerX) * (bar.firstCornerX - bar.secondCornerX) + (bar.firstCornerY - bar.secondCornerY) * (bar.firstCornerY - bar.secondCornerY));
        return d1 == d2;
    }
}
