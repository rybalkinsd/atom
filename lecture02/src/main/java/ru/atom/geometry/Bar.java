package ru.atom.geometry;

public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public void setFirstCornerX(int point){
        firstCornerX = point;
    }

    public void setFirstCornerY(int point) {
        firstCornerY = point;
    }

    public void setSecondCornerX(int point) {
        secondCornerX = point;
    }

    public void setSecondCornerY(int point) {
        secondCornerY = point;
    }

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



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Point
        Bar point = (Bar) o;
        if (this.getFirstCornerX() == point.getFirstCornerX() && this.getSecondCornerY() == point.getSecondCornerY()) {
            return true;
        }
        else
            return false;
    }


    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        else
            return false;
    }
}



