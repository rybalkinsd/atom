package replication;

import model.Player;

import java.io.IOException;

/**
 * @author Alpi
 * @since 31.10.16
 */
public interface Replicator {
  void replicate() throws IOException;
}
