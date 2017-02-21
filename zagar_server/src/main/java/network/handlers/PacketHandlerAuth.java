package network.handlers;

import accountserver.api.Authentification;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuth {
    public PacketHandlerAuth(@NotNull Session session, @NotNull String json) {
        CommandAuth commandAuth;
        try {
            commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
        } catch (JSONDeserializationException e) {
            e.printStackTrace();
            return;
        }
        try {
            if (Authentification.validateToken(commandAuth.getToken())) {
                try {
                    Player player = new Player(commandAuth.getLogin(), Authentification.userDAO.getUserIdByLogin(commandAuth.getLogin()));
                    ApplicationContext.get(ClientConnections.class).registerConnection(player, session);
                    new PacketAuthOk().write(session);
                    ApplicationContext.get(MatchMaker.class).joinGame(player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            try {
                new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
