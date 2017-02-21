package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eugene on 11/24/16.
 */
public class NoConfiguration implements IConfiguration {
    public static final Logger log = LogManager.getLogger(NoConfiguration.class);

    public NoConfiguration() {
        log.warn("No config.ini file found, using default configuration");
    }

    @Override
    public int getPort() {
        return DEFAULT_ACCOUNT_SERVICE_PORT;
    }

    @Override
    public int getWSPort() {
        return DEFAULT_CLIENT_CONNECTION_PORT;
    }

    @Override
    public @NotNull String getMatchMaker() {
        return DEFAULT_MATCH_MAKER;
    }

    @Override
    public @NotNull String getReplicator() {
        return DEFAULT_REPLICATOR;
    }

    @Override
    public @NotNull String[] getServices() {
        return DEFAULT_SERVICES;
    }
}
