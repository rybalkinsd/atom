package engine;

import matchmaker.MatchMaker;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Alpi
 * @since 03.10.16
 */
public class ClientConnectionHandler {
  private static final Logger log = LogManager.getLogger(ClientConnectionHandler.class);
  private Status connectionStatus = Status.UNAUTHORIZED;

   public void handle(GlobalContext globalContext, Player player) {
     if(connectionStatus == Status.UNAUTHORIZED){
       if(!login(player)){
         reportFail("Login failed");
       }
     }
     if(connectionStatus == Status.SEARCHING_FOR_GAME){
       searchForGame(globalContext.get(MatchMaker.class), player);
     }
   }

  private boolean login(Player player) {
    assert(connectionStatus == Status.UNAUTHORIZED);
    //TODO LATER ACCOUNT_SERVER
    connectionStatus = Status.SEARCHING_FOR_GAME;
    return true;
  }

  private void searchForGame(MatchMaker matchMaker, Player player) {
    assert(connectionStatus == Status.UNAUTHORIZED);
    matchMaker.joinGame(player);
    connectionStatus = Status.IN_GAME;
  }

  private void reportFail(String s) {
    log.error(s);
  }


  static enum Status{
    UNAUTHORIZED,
    SEARCHING_FOR_GAME,
    IN_GAME
  }
}
