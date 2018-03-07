package ru.atom.geometry;

//import static jdk.vm.ci.aarch64.AArch64.v1;
//import static jdk.vm.ci.aarch64.AArch64.v3;

public class Bar implements Collider {
    int xLeft;
    int yBarLeft;
    int xBarRight;
    int yBarRight;

    Bar(int xLeft, int yBarLeft, int xBarRight, int yBarRight) {
        this.xLeft = xLeft;
        this.yBarLeft = yBarLeft;
        this.xBarRight = xBarRight;
        this.yBarRight = yBarRight;
    }

    @Override
    public boolean equals(Object o) {
        Bar bar = (Bar) o;

        if (xBarRight < xLeft) {
            int c = xLeft;
            xLeft = xBarRight;
            xBarRight = c;
        }
        if (yBarRight < yBarLeft) {
            int c = yBarLeft;
            yBarLeft = yBarRight;
            yBarRight = c;
        }
        if (bar.xBarRight < bar.xLeft) {
            int c = bar.xLeft;
            bar.xLeft = bar.xBarRight;
            bar.xBarRight = c;
        }
        if (bar.yBarRight < bar.yBarLeft) {
            int c = bar.yBarLeft;
            bar.yBarLeft = bar.yBarRight;
            bar.yBarRight = c;
        }

        if (this == o) return true;
        if (o == null) return false;

        boolean a = (bar.xLeft <= xLeft && xLeft <= bar.xBarRight);
        boolean b = (xLeft <= bar.xLeft && bar.xLeft <= xBarRight);
        boolean c = (bar.yBarLeft <= yBarLeft && yBarLeft <= bar.yBarRight);
        boolean d = (yBarLeft <= bar.yBarLeft && bar.yBarLeft <= yBarRight);
        return (a || b) && (c || d);
    }

    public boolean equals(Point o) {
        Point point = (Point) o;
        boolean q = xLeft <= point.x && point.x <= xBarRight && yBarLeft <= point.y && point.y <= yBarRight;
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
