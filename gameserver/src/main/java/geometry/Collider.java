package geometry;

/**
 * Entity that can physically intersect, like flame and player
 */
//public void pointSelfCollide()
// {
//
//  }
public interface Collider {
    boolean isColliding(Collider other);


}