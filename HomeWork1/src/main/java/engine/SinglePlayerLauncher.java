package engine;

import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.Player;

/**
 * @author Alpi
 * @since 03.10.16
 */
public class SinglePlayerLauncher {
  public static void main(String[] args) {
    GlobalContext globalContext = new GlobalContext();
    globalContext.put(MatchMaker.class, new SinglePlayerMatchMaker());
    Player singlePlayer = new Player();
    ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler();
    clientConnectionHandler.handle(globalContext, singlePlayer);
  }
}
