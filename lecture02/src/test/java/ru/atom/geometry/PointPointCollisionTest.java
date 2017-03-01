package ru.atom.geometry;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PointPointCollisionTest {
    @Test
    public void pointSelfCollide() {
        Collider point = Geometry.createPoint(100, 100);
        assertThat(point.isColliding(point), is(true));
    }

    @Test
    public void pointsEquals() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertThat(point1.equals(point2), is(true));
    }

    @Test
    public void equalPointsCollide() {
        Collider point1 = Geometry.createPoint(100, 100);
        Collider point2 = Geometry.createPoint(100, 100);
        assertThat(point1.isColliding(point2), is(true));
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
}
