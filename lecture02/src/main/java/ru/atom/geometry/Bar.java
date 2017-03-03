package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by IGIntellectual on 03.03.2017.
 */
public class Bar implements Collider {

    private Point firstP;
    private Point secondP;

    public Bar(Point firstP, Point secondP) {
        this.firstP = firstP;
        this.secondP = secondP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bar otherP = (Bar) o;
        return this.firstP.equals(otherP.firstP) && this.secondP.equals(otherP.secondP) || this.firstP.equals(otherP.secondP)
                && this.secondP.equals(otherP.firstP) || this.secondP.getX() == otherP.firstP.getX() 
                && this.secondP.getY() == otherP.secondP.getY()
                && this.firstP.getX() == otherP.secondP.getX() && this.firstP.getY() == otherP.firstP.getY() 
                || this.secondP.getX() == otherP.secondP.getX()
                && this.secondP.getY() == otherP.firstP.getY()
                && this.firstP.getX() == otherP.firstP.getX() && this.firstP.getY() == otherP.secondP.getY();

    }

    @Override
    public boolean isColliding(Collider o) {
        try {
            if (o instanceof Bar) {

                Bar otherP = (Bar) o;

                return this.equals(otherP) || this.checkPoint(otherP.firstP) || this.checkPoint(otherP.secondP)
                        || otherP.checkPoint(this.firstP) || otherP.checkPoint(this.secondP);
            }
            return (o instanceof Point) && this.checkPoint((Point) o);
        } catch (Exception allException) {
            throw new NotImplementedException();
        }
    }

    private boolean checkPoint(Point point) {
        return this.firstP.getX() <= point.getX() && this.secondP.getX() >= point.getX()
                && this.firstP.getY() <= point.getY() && this.secondP.getY() >= point.getY();

    }

}
