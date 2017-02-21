package configuration;

import accountserver.AccountServer;
import matchmaker.MatchMakerMultiplayer;
import mechanics.Mechanics;
import network.ClientConnectionServer;
import org.jetbrains.annotations.NotNull;
import replication.FullStateReplicator;

/**
 * Created by eugene on 11/24/16.
 */
public interface IConfiguration {
    int DEFAULT_ACCOUNT_SERVICE_PORT = 8080;
    int DEFAULT_CLIENT_CONNECTION_PORT = 7000;
    String DEFAULT_MATCH_MAKER = MatchMakerMultiplayer.class.getName();
    String DEFAULT_REPLICATOR = FullStateReplicator.class.getName();
    String[] DEFAULT_SERVICES = new String[]{
            Mechanics.class.getName(),
            AccountServer.class.getName(),
            ClientConnectionServer.class.getName()
    };


    int getPort();
    int getWSPort();

    @NotNull String getMatchMaker();
    @NotNull String getReplicator();

    @NotNull String[] getServices();
}
