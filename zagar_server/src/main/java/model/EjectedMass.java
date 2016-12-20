package model;

import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;

/**
 * Created by xakep666 on 18.12.16.
 */
public class EjectedMass extends Cell {
    @NotNull
    private final Point2D dest;
    private final double acceleration;
    private final double angle;
    private double speed;

    public EjectedMass(@NotNull Point2D start,@NotNull Point2D dest,int mass,double initialSpeed, double acceleration) {
        super(start,mass);
        this.speed=initialSpeed;
        this.dest=dest;
        this.acceleration=acceleration;
        this.angle=Math.atan2(dest.getY()-start.getY(),dest.getX()-start.getX());
    }

    public void tickMove(@NotNull Rectangle2D fieldBorder, @NotNull Duration elapsed) {
        Point2D newCoordinate = new Point2D.Double(
                getCoordinate().getX()+elapsed.toMillis()*speed*Math.cos(angle),
                getCoordinate().getY()+elapsed.toMillis()*speed*Math.sin(angle)
        );
        if (!fieldBorder.contains(newCoordinate)) return;
        if (speed>0) speed+=acceleration*elapsed.toMillis();
        if (speed<0) speed=0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EjectedMass that = (EjectedMass) o;

        if (Double.compare(that.speed, speed) != 0) return false;
        if (Double.compare(that.acceleration, acceleration) != 0) return false;
        if (Double.compare(that.angle, angle) != 0) return false;
        return dest.equals(that.dest);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + dest.hashCode();
        temp = Double.doubleToLongBits(speed);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(acceleration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
