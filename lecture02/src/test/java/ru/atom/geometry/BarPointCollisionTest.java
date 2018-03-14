package ru.atom.geometry;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

//@Ignore
public class BarPointCollisionTest {
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
}
