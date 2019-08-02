package ru.atom.geometry;

/**
 *  ^ Y
 *  |
 *  |
 *  |
 *  |          X
 *  .---------->
 */

public final class Geometry {
    
    private int firstX;
    private int firstY;
    private int secondX;
    private int secondY;
    private String flag;

    private Geometry() {
    }

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     * @return new Bar
     */
    public static Collider createBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        try {
            throw new UnsupportedOperationException();
        }
        catch(UnsupportedOperationException() e){
        }
        Collider collider=new Geometry();
        ((Geometry) collider).firstX=firstCornerX;
        ((Geometry) collider).firstY=firstCornerY;
        ((Geometry) collider).secondX=secondCornerX;
        ((Geometry) collider).secondY=secondCornerY;
        ((Geometry) collider).flag="bar";
//        System.out.println(collider instanceof Geometry);
        return collider;
    }

    /**
     * 2D point
     * @return new Point
     */


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if(o instanceof Geometry) {
            Geometry bar = (Geometry) o;

            if (this.firstX == bar.firstX && this.secondX == bar.secondX || this.firstX == bar.secondX && this.secondX == bar.firstX) {
                if (this.firstY == bar.firstY && this.secondY == bar.secondY || this.firstY == bar.secondY && this.secondY == bar.firstY) {
                    return true;
                } else return false;
            } else return false;
        }
        else{
            Point point = (Point) o;

            if (super.x==point.x){
                if (super.y==point.y){
                    return true;
                }
                else return false;
            }
            else return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        if(other instanceof Geometry&&this instanceof Geometry){
            Geometry bar=(Geometry) other;
            if (this.firstX >= bar.firstX && this.secondX <= bar.secondX ||
                    this.firstX >= bar.secondX && this.secondX <= bar.firstX||
                    this.firstX <= bar.firstX && this.secondX >= bar.secondX ||
                    this.firstX <= bar.secondX && this.secondX >= bar.firstX){
                if (this.firstY >= bar.firstY && this.secondY <= bar.secondY ||
                        this.firstY >= bar.secondY && this.secondY <= bar.firstY||
                        this.firstY <= bar.firstY && this.secondY >= bar.secondY ||
                        this.firstY <= bar.secondY && this.secondY >= bar.firstY)
                    return  true;
                else return false;
            }
            else return false;
        }
        else{
           if(this instanceof Geometry){
               Point point=(Point) other;
               if(this.firstX<=point.x&&this.secondX>=point.x||
                       this.firstX>=point.x&&this.secondX<=point.x){
                   if(this.firstY<=point.y&&this.secondY>=point.y||
                           this.firstY>=point.y&&this.secondY<=point.y)
                       return true;
                   else return false;
               }
               else return false;
           }
           else return false;
        }


    }
}
