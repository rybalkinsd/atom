package ru.atom.geometry;

public class Bar implements Collider{

    private Point p1, p2;

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Bar (int x1, int x2, int y1, int y2){
        //Placing points in correct order (left lower -> right upper)
        this.p1 = new Point(Math.min(x1, x2), Math.min(y1, y2));
        this.p2 = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        // Check if merge
        if (p1.equals(bar.getP1()) && p2.equals(bar.getP2()) ||
                p1.equals(bar.getP2()) && p2.equals(bar.getP1())){
            return true;
        }   else{
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider o) {
        //Check if equals
        if (this.equals(o)) return true;

        //Check if point
        if (o instanceof Point){
            Point point = (Point) o;
            if (p1.getX() <= point.getX() && p2.getX() >= point.getX() &&
                    p1.getY() <= point.getY() && p2.getY() >= point.getY()){
                return true;
            }   else{
                return false;
            }

        }   else{
            //It is a bar after all
            Bar bar = (Bar) o;

            //Checking if not collide
            if (p2.getX() < bar.getP1().getX() || p2.getY() < bar.getP1().getY() ||
                    p1.getX() > bar.getP2().getX() || p1.getY() > bar.getP2().getY()){
                return false;
            }   else{
                return true;
            }
        }
    }
}
