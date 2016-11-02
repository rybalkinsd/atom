package network.handlers;

import accountserver.api.AuthenticationServlet;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import utils.IDGenerator;
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
    if (!AuthenticationServlet.validateToken(commandAuth.getToken())) {
      try {
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        Player player = new Player(ApplicationContext.instance().get(IDGenerator.class).next(), commandAuth.getLogin());
        ApplicationContext.instance().get(ClientConnections.class).registerConnection(player, session);
        new PacketAuthOk().write(session);
        ApplicationContext.instance().get(MatchMaker.class).joinGame(player);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
