package replication;

/**
 * @author Alpi
 * @since 31.10.16
 *
 * Interface for game state repliators
 */
public interface Replicator {
    /**
     * Replicate game state to all clients
     */
    void replicate();
}
