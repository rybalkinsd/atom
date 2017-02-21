package replication;

import main.ApplicationContext;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xakep666 on 26.11.16.
 * <p>
 * Replicates a file content to client (NOT FOR PRODUCTION!!! ONLY FOR TESTS)
 */
public class SimpleJsonReplicator implements Replicator {
    private static final Logger log = LogManager.getLogger(SimpleJsonReplicator.class);
    private static InputStream jsonFileInput = SimpleJsonReplicator.class
            .getClassLoader()
            .getResourceAsStream("testreplic.json");
    private static String json;

    static {
        try {
            if (jsonFileInput == null) {
                log.error("File testreplic.json not found");
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFileInput));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) out.append(line);
                json = out.toString();
            }
        } catch (IOException e) {
            log.fatal(e.getMessage());
            json = null;
        }
    }

    @Override
    public void replicate() {
        if (json == null) return;
        log.debug("Sending test replic {}", json);
        try {
            ApplicationContext.instance().get(ClientConnections.class)
                    .getConnections()
                    .forEach((entry) -> {
                        try {
                            if (entry.getValue().isOpen())
                                entry.getValue().getRemote().sendString(json);
                        } catch (IOException e) {
                            log.fatal(e.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }
    }
}
