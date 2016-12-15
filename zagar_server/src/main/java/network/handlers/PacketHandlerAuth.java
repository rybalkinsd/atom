package network.handlers;

import accountserver.api.AuthenticationServlet;
import accountserver.api.Token;
import accountserver.api.TokenContainer;
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
    Token token = new Token(new Long(commandAuth.getToken()));
    try {
      TokenContainer.validateToken(token);
      Player player = new Player(Player.idGenerator.next(), commandAuth.getLogin());
      ApplicationContext.instance().get(ClientConnections.class).registerConnection(player, session);
      new PacketAuthOk().write(session);
      ApplicationContext.instance().get(MatchMaker.class).joinGame(player);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      try{
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException ee) {
        ee.printStackTrace();
      }
    }

  }
}
