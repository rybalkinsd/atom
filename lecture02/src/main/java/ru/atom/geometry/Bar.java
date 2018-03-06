package ru.atom.geometry;

//import static jdk.vm.ci.aarch64.AArch64.v1;
//import static jdk.vm.ci.aarch64.AArch64.v3;

public class Bar implements Collider{
    int xBarLeft;
    int yBarLeft;
    int xBarRight;
    int yBarRight;
    Bar (int xBarLeft, int yBarLeft, int xBarRight, int yBarRight) {
        this.xBarLeft = xBarLeft;
        this.yBarLeft = yBarLeft;
        this.xBarRight = xBarRight;
        this.yBarRight = yBarRight;
    }
    @Override
    public boolean equals(Object o) {
        Bar bar = (Bar) o;

        if (xBarRight < xBarLeft) {
            int c = xBarLeft; xBarLeft=xBarRight; xBarRight = c;
        }
        if (yBarRight < yBarLeft) {
            int c = yBarLeft; yBarLeft=yBarRight; yBarRight = c;
        }
        if (bar.xBarRight < bar.xBarLeft) {
            int c = bar.xBarLeft; bar.xBarLeft=bar.xBarRight; bar.xBarRight = c;
        }
        if (bar.yBarRight < bar.yBarLeft) {
            int c = bar.yBarLeft; bar.yBarLeft=bar.yBarRight; bar.yBarRight = c;
        }

        if (this == o) return true;
        if (o == null) return false;

        boolean a = (bar.xBarLeft <= xBarLeft && xBarLeft <= bar.xBarRight);
        boolean b = (xBarLeft <= bar.xBarLeft && bar.xBarLeft <= xBarRight);
        boolean c = (bar.yBarLeft <= yBarLeft && yBarLeft <= bar.yBarRight);
        boolean d = (yBarLeft <= bar.yBarLeft && bar.yBarLeft <= yBarRight);
        return (a || b) && (c || d);
    }
    public boolean equals(Point o) {
        Point point = (Point) o;
        boolean q = xBarLeft <= point.x && point.x <= xBarRight && yBarLeft <= point.y && point.y <= yBarRight;
        return q;
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
