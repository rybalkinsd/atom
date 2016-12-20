package network.handlers;

import accountserver.api.AuthenticationServlet;
import leaderboard.LeaderboardImpl;
import main.ApplicationContext;
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
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerAuth {
  private final static Logger log = LogManager.getLogger(PacketHandlerAuth.class);
  public PacketHandlerAuth(@NotNull Session session, @NotNull String json) {
    CommandAuth commandAuth;
    try {
      commandAuth =(CommandAuth) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("Failed to deserialize auth command",ex);
      return;
    }
    if (!AuthenticationServlet.validateToken(commandAuth.getToken())) {
      try {
        new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Invalid user or password").write(session);
      } catch (IOException e) {
        log.error("Failed to send auth fail packet",e);
      }
    } else {

      try {
        ClientConnections connections=ApplicationContext.instance().get(ClientConnections.class);
        for(Map.Entry<Player,Session> entry : connections.getConnections())
          if(entry.getKey().getName().equals(commandAuth.getLogin())) {
            new PacketAuthFail(commandAuth.getLogin(), commandAuth.getToken(), "Player already connected").write(session);
            return;
          }
        Player player = new Player(Player.idGenerator.next(), commandAuth.getLogin());
        connections.registerConnection(player, session);
        new PacketAuthOk(player.getId()).write(session);
        ApplicationContext.instance().get(MatchMaker.class).joinGame(player);
      } catch (IOException e){
        log.error("Failed to send auth fail packet",e);
      }
    }
  }
}
