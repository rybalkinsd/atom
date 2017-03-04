package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Point implements Collider {
    private int firstPointX;
    private int secondPointY;
    private int firstCornerX;
    private int secondCornerY;

    public Point(int x, int y) {
        firstPointX = x;
        secondPointY = y;
    }

   

    public void setFirstPointX(int point){
        firstPointX = point;
    }

    public void setSecondPointY(int point){
        secondPointY = point;
    }

    public int getFirstPointX(){
        return firstPointX;
    }

    public int getSecondPointY(){
        return secondPointY;
    }



    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Point
        Point point = (Point) o;
        if(this.getFirstPointX() == point.getFirstPointX() && this.getSecondPointY() == point.getSecondPointY()) {
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
