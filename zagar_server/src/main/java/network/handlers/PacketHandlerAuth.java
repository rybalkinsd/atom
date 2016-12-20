package network.handlers;

import accountserver.api.auth.AuthenticationApi;
import accountserver.database.users.User;
import accountserver.database.users.UserDao;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandAuth;
import utils.idGeneration.IDGenerator;
import utils.json.JSONDeserializationException;
import utils.json.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuth implements PacketHandler {
    public void handle(@NotNull Session session, @NotNull String json) {
        final ApplicationContext appContext = ApplicationContext.instance();
        try {
            CommandAuth commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
            if (!AuthenticationApi.validateToken(commandAuth.getToken())) {
                log.info("Got invalid token from user '{}'", commandAuth.getLogin());
                new PacketAuthFail(
                        commandAuth.getLogin(),
                        commandAuth.getToken(),
                        "Invalid user or password"
                ).write(session);
            } else {
                User user = appContext.get(UserDao.class).getUserByName(commandAuth.getLogin());
                if (user == null) {
                    log.info("Non-registered user '{}' tried to auth", commandAuth.getLogin());
                    new PacketAuthFail(
                            commandAuth.getLogin(),
                            commandAuth.getToken(),
                            "Error getting user from base"
                    ).write(session);
                    return;
                }
                log.info("User '{}' successfully authorized, creating player and joining game",
                        commandAuth.getLogin());
                int id = appContext.get(IDGenerator.class).next();
                Player player = new Player(id, user);
                appContext.get(ClientConnections.class).registerConnection(player, session);
                new PacketAuthOk().write(session);
                appContext.get(MatchMaker.class).joinGame(player);
            }
        } catch (JSONDeserializationException | IOException e) {
            log.fatal(e.getMessage());
        }
    }
}
