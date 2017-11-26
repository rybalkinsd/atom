package gs.model;

public interface Tickable {
    /**
     * Applies changes to game objects that happen after elapsed time
     */
    void tick(int elapsed);
}
