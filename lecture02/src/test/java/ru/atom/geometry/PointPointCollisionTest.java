package ru.atom.geometry;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PointPointCollisionTest {
    @Test
    public void pointSelfCollide() {
        Collider point = Geometry.createPoint(100, 100);
        assertTrue(point.isColliding(point));
    }

    @Test
    public void pointsEquals() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertTrue(point1.equals(point2));
    }

    @Test
    public void equalPointsCollide() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertTrue(point1.isColliding(point2));
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
}
