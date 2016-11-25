package ticker;

/**
 * Created by apomosov on 14.05.16.
 */
public interface Tickable {
    void tick(long elapsedNanos);
}
