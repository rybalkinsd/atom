package network.handlers;

import accountserver.api.AuthenticationServlet;
import accountserver.api.Token;
import accountserver.api.TokenContainer;
import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import model.Player;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import network.packets.PacketAuthOk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuth;
import utils.IDGenerator;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketHandlerAuth {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  public PacketHandlerAuth(@NotNull Session session, @NotNull String json) {
    CommandAuth commandAuth;
    try {
      commandAuth = JSONHelper.fromJSON(json, CommandAuth.class);
    } catch (JSONDeserializationException e) {
      log.error(e);
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
      log.error(e);
    } catch (Exception e) {
      try{
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException ee) {
        log.error(ee);
      }
    }

  }
}
