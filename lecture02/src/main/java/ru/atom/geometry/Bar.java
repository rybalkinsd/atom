package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by IGIntellectual on 03.03.2017.
 */
public class Bar implements Collider {

    private Point firstP, secondP;

    public Bar(Point firstP, Point secondP) {
        this.firstP = firstP;
        this.secondP = secondP;
//        firstY = firstCornerY;
//        secondX = secondCornerX;
//        secondY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bar other_p = (Bar) o;
        return this.firstP.equals(other_p.firstP) && this.secondP.equals(other_p.secondP) || this.firstP.equals(other_p.secondP) && this.secondP.equals(other_p.firstP) || this.secondP.getX() == other_p.firstP.getX() && this.secondP.getY() == other_p.secondP.getY()
                && this.firstP.getX() == other_p.secondP.getX() && this.firstP.getY() == other_p.firstP.getY() || this.secondP.getX() == other_p.secondP.getX() && this.secondP.getY() == other_p.firstP.getY()
                && this.firstP.getX() == other_p.firstP.getX() && this.firstP.getY() == other_p.secondP.getY();

    }

    @Override
    public boolean isColliding(Collider o) {
        try {
            if (o instanceof Bar) {

                Bar other_p = (Bar) o;

                return this.equals(other_p) || this.CheckPoint(other_p.firstP) || this.CheckPoint(other_p.secondP) || other_p.CheckPoint(this.firstP) || other_p.CheckPoint(this.secondP);
            }
            return (o instanceof Point) && this.CheckPoint((Point) o);
        } catch (Exception e) {
            throw new NotImplementedException();
        }
    }

    private boolean CheckPoint(Point point) {
        return this.firstP.getX() <= point.getX() && this.secondP.getX() >= point.getX() && this.firstP.getY() <= point.getY() && this.secondP.getY() >= point.getY();

    }

}
