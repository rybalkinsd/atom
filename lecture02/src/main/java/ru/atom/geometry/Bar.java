package ru.atom.geometry;


import java.util.ArrayList;

import java.util.List;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY,
               int secondCornerX, int secondCornerY) {
        this.firstCornerX = Math.min(firstCornerX, secondCornerX);
        this.firstCornerY = Math.min(firstCornerY, secondCornerY);
        this.secondCornerX = Math.max(firstCornerX, secondCornerX);
        this.secondCornerY = Math.max(firstCornerY, secondCornerY);
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
        return secondCornerX;
    }

    public void setSecondCornerX(int secondCornerX) {
        this.secondCornerX = secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    protected void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            if (this.equals(other)) {
                return true;
            }
            Bar bar = (Bar) other;
            if (bar.getSecondCornerX() >= this.getFirstCornerX()
                    && bar.getFirstCornerX() <= this.getSecondCornerX()) {
                bar.setFirstCornerX(this.getFirstCornerX());
            }
            if (bar.getFirstCornerX() <= this.getSecondCornerX()
                    && bar.getSecondCornerX() >= this.getFirstCornerX()) {
                bar.setSecondCornerX(this.getSecondCornerX());
            }
            if (bar.getSecondCornerY() >= this.getFirstCornerY()
                    && bar.getFirstCornerY() <= this.getSecondCornerY()) {
                bar.setFirstCornerY(this.getFirstCornerY());
            }
            if (bar.getFirstCornerY() <= this.getSecondCornerY()
                    && bar.getSecondCornerY() >= this.getFirstCornerY()) {
                bar.setSecondCornerY(this.getSecondCornerY());
            }
            return this.getFirstCornerX() == bar.getFirstCornerX()
                    && this.getFirstCornerY() == bar.getFirstCornerY()
                    && this.getSecondCornerX() == bar.getSecondCornerX()
                    && this.getSecondCornerY() == bar.getSecondCornerY();

        } else {
            if (other instanceof Point) {
                return ((Point) other).getX() >= this.firstCornerX
                        && ((Point) other).getX() <= this.secondCornerX
                        && ((Point) other).getY() >= this.firstCornerY
                        && ((Point) other).getY() <= this.secondCornerY;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return this.getFirstCornerX() == bar.getFirstCornerX()
                && this.getFirstCornerY() == bar.getFirstCornerY()
                && this.getSecondCornerX() == bar.getSecondCornerX()
                && this.getSecondCornerY() == bar.getSecondCornerY();
    }
}
