package ru.atom.geometry;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class BarBarCollisionTest {
    @Test
    public void barSelfCollide() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar.isColliding(bar));
    }

    @Test
    public void barsEquals() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.equals(bar2));
    }

    @Test
    public void barIsNotOriented1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 100, 0, 0);
        assertTrue(bar1.equals(bar2));
    }

    @Test
    public void barIsNotOriented2() {
        Collider bar1 = Geometry.createBar(0, 100, 100, 0);
        Collider bar2 = Geometry.createBar(100, 100, 0, 0);
        assertTrue(bar1.equals(bar2));
    }

    @Test
    public void barIsNotOriented3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 0, 0, 100);
        assertTrue(bar1.equals(bar2));
    }

    @Test
    public void equalBarsCollide1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void equalBarsCollide2() {
        Collider bar1 = Geometry.createBar(0, 100, 100, 0);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void equalBarsCollide3() {
        Collider bar1 = Geometry.createBar(100, 0, 0, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void pointsNotCollide1() {
        Collider point1 = Geometry.createPoint(200, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertFalse(point1.isColliding(point2));
    }

    @Test
    public void pointsNotCollide2() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(200, 100);
        assertFalse(point1.isColliding(point2));
    }

    @Test
    public void pointsNotCollide3() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(200, 200);
        assertFalse(point1.isColliding(point2));
    }


    @Test
    public void pointInsideBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(50, 50);
        assertTrue(bar.isColliding(point));
    }

    @Test
    public void pointOnCornerOfBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 0);
        assertTrue(bar.isColliding(point));
    }

    @Test
    public void pointOnBorderOfBar() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 50);
        assertTrue(bar.isColliding(point));
    }

    @Test
    public void pointOutsideOfBar1() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(0, 150);
        assertFalse(bar.isColliding(point));
    }

    @Test
    public void pointOutsideOfBar2() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(150, 0);
        assertFalse(bar.isColliding(point));
    }

    @Test
    public void pointOutsideOfBar3() {
        Collider bar = Geometry.createBar(0, 0, 100, 100);
        Collider point = Geometry.createPoint(150, 150);
        assertFalse(bar.isColliding(point));
    }

    @Test
    public void barIntersectsBar1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(50, 0, 150, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar2() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 50, 100, 150);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(50, 50, 150, 150);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar14() {
        Collider bar1 = Geometry.createBar(50, 0, 150, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar5() {
        Collider bar1 = Geometry.createBar(0, 50, 100, 150);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar6() {
        Collider bar1 = Geometry.createBar(50, 50, 150, 150);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar7() {
        Collider bar1 = Geometry.createBar(50, 0, 150, 100);
        Collider bar2 = Geometry.createBar(0, 50, 100, 150);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBar8() {
        Collider bar1 = Geometry.createBar(0, 50, 100, 150);
        Collider bar2 = Geometry.createBar(50, 0, 150, 100);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBarByBorder1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 100, 100, 200);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIntersectsBarByCorner() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(100, 100, 200, 200);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barIncludesBar() {
        Collider bar1 = Geometry.createBar(0, 0, 200, 200);
        Collider bar2 = Geometry.createBar(50, 50, 150, 150);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar1() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(0, 150, 100, 250);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar2() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(150, 0, 250, 100);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar3() {
        Collider bar1 = Geometry.createBar(0, 0, 100, 100);
        Collider bar2 = Geometry.createBar(150, 150, 200, 200);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar4() {
        Collider bar1 = Geometry.createBar(0, 150, 100, 250);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar5() {
        Collider bar1 = Geometry.createBar(150, 0, 250, 100);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barDoesNotIntersectBar6() {
        Collider bar1 = Geometry.createBar(150, 150, 200, 200);
        Collider bar2 = Geometry.createBar(0, 0, 100, 100);
        assertFalse(bar1.isColliding(bar2));
    }

    @Test
    public void barCrossesBar1() {
        Collider bar1 = Geometry.createBar(0, 50, 150, 100);
        Collider bar2 = Geometry.createBar(50, 0, 100, 150);
        assertTrue(bar1.isColliding(bar2));
    }

    @Test
    public void barCrossesBar2() {
        Collider bar1 = Geometry.createBar(50, 0, 100, 150);
        Collider bar2 = Geometry.createBar(0, 50, 150, 100);
        assertTrue(bar1.isColliding(bar2));
    }
}
