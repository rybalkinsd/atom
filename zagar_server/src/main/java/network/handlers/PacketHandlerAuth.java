package network.handlers;


import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import model.TokensCollection;
import model.TokensCollectionImpl;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuth {
  private static final TokensCollection data=new TokensCollectionImpl();
  public PacketHandlerAuth(@NotNull Session session, @NotNull String json) {
    CommandAuth commandAuth;
    try {
      commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    try {
      data.validateToken(commandAuth.getToken(), 0, "it shall not be");
      Player player = new Player(Player.idGenerator.next(), commandAuth.getLogin());
      ApplicationContext.instance().get(ClientConnections.class).registerConnection(player, session);
      new PacketAuthOk().write(session);
      ApplicationContext.instance().get(MatchMaker.class).joinGame(player);
    } catch (Exception e) {
      try {
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException m) {
        m.printStackTrace();
      }

    }
  }
}
