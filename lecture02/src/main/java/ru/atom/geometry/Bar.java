package ru.atom.geometry;

//import static jdk.vm.ci.aarch64.AArch64.v1;
//import static jdk.vm.ci.aarch64.AArch64.v3;

public class Bar implements Collider {
    int xleft;
    int yleft;
    int xright;
    int yright;

    Bar(int xleft, int yleft, int xright, int yright) {
        this.xleft = xleft;
        this.yleft = yleft;
        this.xright = xright;
        this.yright = yright;
    }

    @Override
    public boolean equals(Object o) {
        Bar bar = (Bar) o;

        if (xright < xleft) {
            int c = xleft;
            xleft = xright;
            xright = c;
        }
        if (yright < yleft) {
            int c = yleft;
            yleft = yright;
            yright = c;
        }
        if (bar.xright < bar.xleft) {
            int c = bar.xleft;
            bar.xleft = bar.xright;
            bar.xright = c;
        }
        if (bar.yright < bar.yleft) {
            int c = bar.yleft;
            bar.yleft = bar.yright;
            bar.yright = c;
        }

        if (this == o) return true;
        if (o == null) return false;

        boolean avar = (bar.xleft <= xleft && xleft <= bar.xright);
        boolean bvar = (xleft <= bar.xleft && bar.xleft <= xright);
        boolean cvar = (bar.yleft <= yleft && yleft <= bar.yright);
        boolean dvar = (yleft <= bar.yleft && bar.yleft <= yright);
        return (avar || bvar) && (cvar || dvar);
    }

    public boolean equals(Point o) {
        Point point = (Point) o;
        boolean qvar = xleft <= point.x && point.x <= xright && yleft <= point.y && point.y <= yright;
        return qvar;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return this.equals((Bar) other);
        }
        if (other instanceof Point) {
            return this.equals((Point) other);
        }
        return false;
    }
}
