package ru.atom.geometry;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BarBarCollisionTest {
    @Test
    public void barSelfCollide() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar.isColliding(bar), is(true));
    }

    @Test
    public void barsEquals() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.equals(bar2), is(true));
    }

    @Test
    public void barIsNotOriented1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 100, 0, 0);
        assertThat(bar1.equals(bar2), is(true));
    }

    @Test
    public void barIsNotOriented2() {
        Collider bar1 = Geometry.createBar(0, 100, 100, 0);
        Collider bar2 = Geometry.createBar(100, 100, 0, 0);
        assertThat(bar1.equals(bar2), is(true));
    }

    @Test
    public void barIsNotOriented3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 0, 0, 100);
        assertThat(bar1.equals(bar2), is(true));
    }

    @Test
    public void equalBarsCollide1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void equalBarsCollide2() {
        Collider bar1 = Geometry.createBar(0, 100, 100, 0);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void equalBarsCollide3() {
        Collider bar1 = Geometry.createBar(100, 0, 0, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void pointsNotCollide1() {
        Collider point1 = Geometry.createPoint(200, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertThat(point1.isColliding(point2), is(false));
    }

    @Test
    public void pointsNotCollide2() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(200, 100);
        assertThat(point1.isColliding(point2), is(false));
    }

    @Test
    public void pointsNotCollide3() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(200, 200);
        assertThat(point1.isColliding(point2), is(false));
    }


    @Test
    public void pointInsideBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(50, 50);
        assertThat(bar.isColliding(point), is(true));
    }

    @Test
    public void pointOnCornerOfBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 0);
        assertThat(bar.isColliding(point), is(true));
    }

    @Test
    public void pointOnBorderOfBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 50);
        assertThat(bar.isColliding(point), is(true));
    }

    @Test
    public void pointOutsideOfBar1() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 150);
        assertThat(bar.isColliding(point), is(false));
    }

    @Test
    public void pointOutsideOfBar2() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(150, 0);
        assertThat(bar.isColliding(point), is(false));
    }

    @Test
    public void pointOutsideOfBar3() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(150, 150);
        assertThat(bar.isColliding(point), is(false));
    }

    @Test
    public void barIntersectsBar1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(50, 0, 150, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBar2() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 50, 100, 150);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBar3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(50, 50, 150, 150);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBar14() {
        Collider bar1 = Geometry.createBar(50, 0, 150, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBar5() {
        Collider bar1 = Geometry.createBar(0, 50, 100, 150);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBar6() {
        Collider bar1 = Geometry.createBar(50, 50, 150, 150);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBarByBorder1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 100, 100, 200);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIntersectsBarByCorner() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 100, 200, 200);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barIncludesBar() {
        Collider bar1 = Geometry.createBar(0, 0, 200, 200);
        Collider bar2 = Geometry.createBar(50, 50, 150, 150);
        assertThat(bar1.isColliding(bar2), is(true));
    }

    @Test
    public void barDoesNotIntersectBar1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 150, 100, 250);
        assertThat(bar1.isColliding(bar2), is(false));
    }

    @Test
    public void barDoesNotIntersectBar2() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(150, 0, 250, 100);
        assertThat(bar1.isColliding(bar2), is(false));
    }

    @Test
    public void barDoesNotIntersectBar3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(150, 150, 200, 200);
        assertThat(bar1.isColliding(bar2), is(false));
    }

    @Test
    public void barDoesNotIntersectBar4() {
        Collider bar1 = Geometry.createBar(0, 150, 100, 250);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(false));
    }

    @Test
    public void barDoesNotIntersectBar5() {
        Collider bar1 = Geometry.createBar(150, 0, 250, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(false));
    }

    @Test
    public void barDoesNotIntersectBar6() {
        Collider bar1 = Geometry.createBar(150, 150, 200, 200);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertThat(bar1.isColliding(bar2), is(false));
    }
}
