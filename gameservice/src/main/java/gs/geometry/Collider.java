package gs.geometry;

public interface Collider {
    /**
     * @return true if Colliders geometrically intersect
     */
    boolean isColliding(Collider other);
}
