package ru.atom.geometry;

public class Bar implements Collider{
    private Point firstCorner;
    private Point secondCorner;

    public Bar (int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY){
        this.firstCorner = new Point(Math.min(firstCornerX, secondCornerX),Math.min(firstCornerY, secondCornerY));
        this.secondCorner = new Point(Math.max(firstCornerX, secondCornerX),Math.max(firstCornerY, secondCornerY));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point){
            Point point = (Point) other;
            if(this.firstCorner.getX()<= point.getX() && this.secondCorner.getX()>= point.getX() &&
            this.firstCorner.getY()<= point.getY() && this.secondCorner.getY()>= point.getY()){
                return true;
            }
            return false;
        } else {
            Bar bar = (Bar) other;
            //firstBar left or right by X secondBar
            if (this.secondCorner.getX() < bar.firstCorner.getX() || this.firstCorner.getX() > bar.secondCorner.getX()) {
                return false;
            }
            ////firstBar bottom or top by Y secondBar
            if (this.secondCorner.getY() < bar.firstCorner.getY() || this.firstCorner.getY() > bar.secondCorner.getY()) {
                return false;
            }
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return this.firstCorner.equals(bar.firstCorner) && this.secondCorner.equals(bar.secondCorner);
    }
}
