package model;

/**
 * Created by venik on 08.12.16.
 */
public class DoubleVector {
    private  double x;
    private  double y;

    public DoubleVector(){x=0;y=0;}

    public DoubleVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoubleVector(DoubleVector doubleVector) {
        this.x = doubleVector.x;
        this.y = doubleVector.y;
    }

    public double getX(){return x;}
    public double getY(){return y;}

    public DoubleVector setX(double x){
        this.x = x;
        return this;
    }

    public DoubleVector setY(double y){
        this.y = y;
        return this;
    }

    public DoubleVector multi(double a){
        x = x*a;
        y = y*a;
        return this;
    }

    public DoubleVector add(DoubleVector doubleVector){
        x = x + doubleVector.getX();
        y = y + doubleVector.getY();
        return  this;
    }

    public DoubleVector sub(DoubleVector doubleVector){
        x = x - doubleVector.getX();
        y = y - doubleVector.getY();
        return  this;
    }

    public DoubleVector normalize(){
        double r = Math.sqrt(x*x+y*y);
        if (r == 0){
            x = 0;
            y = 0;
            return this;
        }
        x = x/r;
        y = y/r;
        return  this;
    }

    public double length(){
        return Math.sqrt(x*x+y*y);
    }

    public static DoubleVector zero(){
        return  new DoubleVector(0,0);
    }
}

