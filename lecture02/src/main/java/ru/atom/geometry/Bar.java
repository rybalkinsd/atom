package ru.atom.geometry;


public class Bar implements Collider {
    int x1;
    int y1;
    int x2;
    int y2;
         

    Bar(int xx1, int yy1, int xx2, int yy2) {
        x1 = xx1;
        y1 = yy1;
        x2 = xx2;
        y2 = yy2;
    }


    public boolean isColliding(Collider o1) {
        if (this == o1) return true;
        if (o1 == null) return false;
              
        if (getClass() == o1.getClass()) {
            Bar o2 = (Bar) o1;
                   
            if (((((o2.x1 >= x1) && (o2.x1 <= x2)) || ((o2.x1 >= x2) && (o2.x1 <= x1)))
                    && (((o2.y1 >= y1) && (o2.y1 <= y2)) || ((o2.y1 >= y2) && (o2.y1 <= y1))))
                    || ((((o2.x2 >= x1) && (o2.x2 <= x2)) || ((o2.x2 >= x2) && (o2.x2 <= x1)))
                    && (((o2.y2 >= y1) && (o2.y2 <= y2)) || ((o2.y2 >= y2) && (o2.y2 <= y1)))))
                return true;
        } else {
            Point o2 = (Point) o1;
            if ((((o2.x >= x1) && (o2.x <= x2)) || ((o2.x >= x2) && (o2.x <= x1)))
                    && (((o2.y >= y1) && (o2.y <= y2)) || ((o2.y >= y2) && (o2.y <= y1))))
                return true;
            return false;
        }
        return false;
    }


 
    @Override
    public boolean equals(Object o1) {
        if (this == o1)
            return true;
        if (o1 == null || getClass() != o1.getClass())
            return false;

        // cast from Object to Point
        Bar o2 = (Bar) o1;

        // your code here
        if (((o2.x1 == x1) && (o2.y1 == y1) && (o2.x2 == x2) && (o2.y2 == y2))
                || ((o2.x1 == x2) && (o2.y1 == y2) && (o2.x2 == x1) && (o2.y2 == y1))
                || ((o2.x1 == x1) && (o2.y1 == y2) && (o2.x2 == x2) && (o2.y2 == y1))
                || ((o2.x1 == x2) && (o2.y1 == y1) && (o2.x2 == x1) && (o2.y2 == y2)))
            return true;
        return false;
    } 
}
