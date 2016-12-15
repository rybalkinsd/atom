package mechanics;

/**
 * Created by eugene on 11/22/16.
 */


public interface MechanicConstants {
    int MINIMAL_MASS = 16;
    int EJECTED_MASS = 10;

    float MINIMAL_SPEED = 0.1f;
    float MAXIMAL_SPEED = 10f;
    float SPLIT_SPEED = 15f;

    float EJECT_SPEED = 18f;

    float VISCOSITY_DECREMENT = 1000f; // milliseconds
    float ATTRACTION_DECREMENT = 30_000f;

}
