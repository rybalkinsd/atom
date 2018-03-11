package ru.atom.geometry;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int SecondCornerX;
    private int SecondCornerY;

    public int lenX() {
        return Math.abs(this.getFirstCornerX() - this.getSecondCornerX());
    }
    public int lenY() {
        return Math.abs(this.getFirstCornerY() - this.getSecondCornerY());
    }

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.setFirstCornerX(Math.min(firstCornerX, secondCornerX));
        this.setFirstCornerY(Math.min(firstCornerY, secondCornerY));
        this.setSecondCornerX(Math.max(firstCornerX, secondCornerX));
        this.setSecondCornerY(Math.max(firstCornerY, secondCornerY));

    }
    @Override
    public boolean isColliding(Collider other) {
        if(other instanceof Point) {
            Point point = (Point) other;
            return (point.getX() >= this.getFirstCornerX() && point.getX() <= this.getSecondCornerX()
                    && point.getY() >= this.getFirstCornerY() && point.getY() <= this.getSecondCornerY());
        } else if(other instanceof Bar) {
            Bar bar = (Bar) other;
            Point a,b,c,d;
            a = new Point(bar.getFirstCornerX(), bar.getFirstCornerY());
            b = new Point(bar.getSecondCornerX(), bar.getFirstCornerY());
            c = new Point(bar.getFirstCornerX(), bar.getSecondCornerY());
            d = new Point(bar.getSecondCornerX(), bar.getSecondCornerY());
            if(this.isColliding(a) || this.isColliding(b) || this.isColliding(c) || this.isColliding(d)) return true;
        } //else return false;
        return false;
    }
    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true;
        else {
            if(other instanceof Bar) {
                Bar bar = (Bar) other;
                if(this.lenX() == bar.lenX() && this.lenY() == bar.lenY()
                        || this.lenX() == bar.lenY() && this.lenY() == bar.lenX())
                    return true;
            }
            return false;
        }
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public void setFirstCornerX(int firstCornerX) {
        this.firstCornerX = firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public void setFirstCornerY(int firstCornerY) {
        this.firstCornerY = firstCornerY;
    }

    public int getSecondCornerX() {
        return SecondCornerX;
    }

    public void setSecondCornerX(int secondCornerX) {
        SecondCornerX = secondCornerX;
    }

    public int getSecondCornerY() {
        return SecondCornerY;
    }

    public void setSecondCornerY(int secondCornerY) {
        SecondCornerY = secondCornerY;
    }
}
