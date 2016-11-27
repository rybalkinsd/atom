package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandAuthFail;
import protocol.CommandLeaderBoard;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;

public class PacketLeaderBoard {
  @NotNull
  private static final Logger log = LogManager.getLogger(PacketLeaderBoard.class);
  @NotNull
  private final String[] leaderBoard;

  private String[] getLeaderBoard(){
    return leaderBoard;
  }

  public PacketLeaderBoard(@NotNull String[] leaderBoard) {
    this.leaderBoard = leaderBoard;
  }
  public PacketLeaderBoard(@NotNull String leaderBoard) {
    PacketLeaderBoard packetLeaderBoard = new PacketLeaderBoard(new String[0]);
    try {
        packetLeaderBoard = JSONHelper.fromJSON(leaderBoard,PacketLeaderBoard.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
    }

    this.leaderBoard = packetLeaderBoard.getLeaderBoard();
  }

  public void write(@NotNull Session session) throws IOException {
    String msg = JSONHelper.toJSON(new CommandLeaderBoard(leaderBoard));
    log.info("Sending [" + msg + "]");
    session.getRemote().sendString(msg);
  }
}
