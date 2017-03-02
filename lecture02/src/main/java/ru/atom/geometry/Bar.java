package ru.atom.geometry;


/**
 * Created by Ксения on 01.03.2017.
 */

public class Bar implements Collider {
    int x1;
    int y1;
    int x2;
    int y2;

    public boolean isColliding(Collider other) {

        Bar thisStandarded = ((Bar) this).getStandardBar();
        if (other.getClass() == Point.class) {
           other = (Point) other;
           if (thisStandarded.x1 <= ((Point) other).x && thisStandarded.x2 >= ((Point) other).x
                   && thisStandarded.y1 <= ((Point) other).y && thisStandarded.y2 >= ((Point) other).y) return true;
           return false;
        }
        if (other.getClass() == Bar.class) {
            other = (Bar) other;
            Bar bar = ((Bar) other).getStandardBar();
            if (((thisStandarded.x1 <= ((Bar) bar).x1 && thisStandarded.x2 >= ((Bar) bar).x1)
                    || (thisStandarded.x1 <= ((Bar) bar).x2 && thisStandarded.x2 >= ((Bar) bar).x2))
                    && ((thisStandarded.y1 <= ((Bar) bar).y1 && thisStandarded.y2 >= ((Bar) bar).y1)
                    || (thisStandarded.y1 <= ((Bar) bar).y2 && thisStandarded.y2 >= ((Bar) bar).y2)
                    || (thisStandarded.y1 > ((Bar) bar).y1 && thisStandarded.y2 < ((Bar) bar).y2))) return true;
            if (((thisStandarded.y1 <= ((Bar) bar).y1 && thisStandarded.y2 >= ((Bar) bar).y1)
                    || (thisStandarded.y1 <= ((Bar) bar).y2 && thisStandarded.y2 >= ((Bar) bar).y2))
                    && ((thisStandarded.x1 <= ((Bar) bar).x1 && thisStandarded.x2 >= ((Bar) bar).x1)
                    || (thisStandarded.x1 <= ((Bar) bar).x2 && thisStandarded.x2 >= ((Bar) bar).x2)
                    || (thisStandarded.x1 > ((Bar) bar).x1 && thisStandarded.x2 < ((Bar) bar).x2))) return true;
            return false;
        }
        return false;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = ((Bar) o).getStandardBar();
        Bar thisStandarded = ((Bar) this).getStandardBar();
        if (thisStandarded.x1 == ((Bar) bar).x1 && thisStandarded.x2 == ((Bar) bar).x2 && thisStandarded.y1 == ((Bar) bar).y1 && thisStandarded.y2 == ((Bar) bar).y2) return true;
        else return false;

    }

    public Bar getStandardBar() {
        Bar standard = new Bar();
        if (this.x1 < this.x2) {
            standard.x1 = this.x1;
            standard.x2 = this.x2;
        }
        else {
            standard.x1 = this.x2;
            standard.x2 = this.x1;
        };
        if (this.y1 < this.y2) {
            standard.y1 = this.y1;
            standard.y2 = this.y2;
        }
        else {
            standard.y1 = this.y2;
            standard.y2 = this.y1;
        }
        return standard;
    }
}
