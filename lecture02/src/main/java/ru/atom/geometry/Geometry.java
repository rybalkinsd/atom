package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * ^ Y
 * |
 * |
 * |
 * |          X
 * .---------->
 */

public final class Geometry {

    private Geometry() {
    }

    public static class Bar implements Collider {
        Point fp;
        Point sp;

        public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
            fp = new Point(firstPointX, firstCornerY);
            sp = new Point(secondCornerX, secondCornerY);
            if (fp.equals(sp)) {
                throw new NotImplementedException();
            }
        }


        @Override
        public boolean isColliding(Collider other) {
            if (this == other)
                return true;
            if (other == null)
                return false;
            if (other.getClass() == this.getClass()) {
                int max1, max2, max3, max4,
                        may1, may2, may3, may4;
                Bar bar = (Bar) other;

                if (this.fp.y <= this.sp.y) {
                    may1 = this.fp.y;
                    may2 = this.sp.y;
                } else {
                    may2 = this.fp.y;
                    may1 = this.sp.y;
                }
                if (this.fp.x <= this.sp.x) {
                    max1 = this.fp.x;
                    max2 = this.sp.x;
                } else {
                    max2 = this.fp.x;
                    max1 = this.sp.x;
                }
                if (bar.fp.x <= bar.sp.x) {
                    max3 = bar.fp.x;
                    max4 = bar.sp.x;
                } else {
                    max4 = bar.fp.x;
                    max3 = bar.sp.x;
                }
                if (bar.fp.y <= bar.sp.y) {
                    may3 = bar.fp.y;
                    may4 = bar.sp.y;
                } else {
                    may4 = bar.fp.y;
                    may3 = bar.sp.y;
                }
                if (!(max1 > max4) && !(max2 < max3) && !(may1 > may4) && !(may2 < may3))
                    return true;


            }
            if (other instanceof Point) {
                Point point = (Point) other;
                int max1, max2, may1, may2;
                if (this.fp.x <= this.sp.x) {
                    max1 = this.fp.x;
                    max2 = this.sp.x;
                } else {
                    max2 = this.fp.x;
                    max1 = this.sp.x;
                }
                if (this.fp.y <= this.sp.y) {
                    may1 = this.fp.y;
                    may2 = this.sp.y;
                } else {
                    may2 = this.fp.y;
                    may1 = this.sp.y;
                }
                if (!(point.x > max2) && !(point.x < max1) && !(point.y > may2) && !(point.y < may1))
                    return true;
            }
            return false;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null)
                return false;
            if (o.getClass() == this.getClass()) {
                int max1, max2, max3, max4,
                        may1, may2, may3, may4;
                Bar bar = (Bar) o;
                if (this.fp.x <= this.sp.x) {
                    max1 = this.fp.x;
                    max2 = this.sp.x;
                } else {
                    max2 = this.fp.x;
                    max1 = this.sp.x;
                }
                if (this.fp.y <= this.sp.y) {
                    may1 = this.fp.y;
                    may2 = this.sp.y;
                } else {
                    may2 = this.fp.y;
                    may1 = this.sp.y;
                }
                if (bar.fp.x <= bar.sp.x) {
                    max3 = bar.fp.x;
                    max4 = bar.sp.x;
                } else {
                    max4 = bar.fp.x;
                    max3 = bar.sp.x;
                }
                if (bar.fp.y <= bar.sp.y) {
                    may3 = bar.fp.y;
                    may4 = bar.sp.y;
                } else {
                    may4 = bar.fp.y;
                    may3 = bar.sp.y;
                }
                if (max1 == max3 && max2 == max4 && may1 == may3 && may2 == may4) return true;


            }
            return false;
        }
    }

    /**
     * Bar is a rectangle, which borders are parallel to coordinate axis
     * Like selection bar in desktop, this bar is defined by two opposite corners
     * Bar is not oriented
     * (It is not relevant, which opposite corners you choose to define bar)
     *
     * @return new Bar
     */
    public static Collider createBar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        return new Bar(firstPointX, firstCornerY, secondCornerX, secondCornerY);
    }

    /**
     * 2D point
     *
     * @return new Point
     */
    public static Collider createPoint(int x, int y) {
        return new Point(x, y);
    }

}
