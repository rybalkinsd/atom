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
        try {
            CommandAuth commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
            if (!AuthenticationApi.validateToken(commandAuth.getToken())) {
                new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
            } else {
                User user = ApplicationContext.instance().get(UserDao.class).getUserByName(commandAuth.getLogin());
                if (user == null) {
                    new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Error getting user from base")
                            .write(session);
                    return;
                }
                int id = ApplicationContext.instance().get(IDGenerator.class).next();
                Player player = new Player(id, user);
                ApplicationContext.instance().get(ClientConnections.class).registerConnection(player, session);
                new PacketAuthOk().write(session);
                ApplicationContext.instance().get(MatchMaker.class).joinGame(player);
            }
        } catch (JSONDeserializationException | IOException e) {
            e.printStackTrace();
        }
    }
}
