package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ini4j.Profile;
import org.ini4j.Wini;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by eugene on 11/24/16.
 */
public class IniConfiguration implements IConfiguration {
    private static final Logger log = LogManager.getLogger(IniConfiguration.class);

    private final Wini wini;
    private final Path iniPath;
    @NotNull private final Profile.Section mainSection;

    public IniConfiguration(String sourcePath) throws IOException {
        iniPath = Paths.get(sourcePath);
        this.wini = new Wini(new FileInputStream(iniPath.toFile()));
        this.mainSection = wini.get("main");
    }

    @Override
    public int getPort() {
        String port = mainSection.get("accountServerPort");
        if (port == null) {
            log.warn("Using default value for 'accountServerPort' = " + String.valueOf(DEFAULT_ACCOUNT_SERVICE_PORT));
            return DEFAULT_ACCOUNT_SERVICE_PORT;
        }
        return Integer.valueOf(port);
    }

    @Override
    public int getWSPort() {
        String port = mainSection.get("clientConnectionPort");
        if (port == null) {
            log.warn("Using default value for 'clientConnectionPort' = " + String.valueOf(DEFAULT_CLIENT_CONNECTION_PORT));
            return DEFAULT_CLIENT_CONNECTION_PORT;
        }
        return Integer.valueOf(port);
    }

    @NotNull
    @Override
    public String getMatchMaker() {
        return mainSection.getOrDefault("matchMaker", DEFAULT_MATCH_MAKER);
    }

    @NotNull
    @Override
    public String getReplicator() {
        return mainSection.getOrDefault("replicator", DEFAULT_REPLICATOR);
    }

    @NotNull
    @Override
    public String[] getServices() {
        String services = mainSection.get("services");
        if (services == null) {
            log.warn("Using default value for 'services'");
            return DEFAULT_SERVICES;
        }

        return services.split(",");
    }
}
