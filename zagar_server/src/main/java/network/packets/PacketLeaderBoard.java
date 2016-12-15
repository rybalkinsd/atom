package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.jetbrains.annotations.NotNull;
import protocol.CommandLeaderBoard;
import utils.JSONHelper;

import java.io.IOException;

public class PacketLeaderBoard {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketLeaderBoard.class);
  @NotNull
  private final String[] leaderBoard;

  public PacketLeaderBoard(@NotNull String[] leaderBoard) {
    this.leaderBoard = leaderBoard;
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toSerial(new CommandLeaderBoard(leaderBoard));
    log.info("Sending [" + msg + "]");
    try {
      session.getRemote().sendString(msg);
    } catch (WebSocketException ex) {
      log.error("Failed to send", ex);
    }
  }
}
