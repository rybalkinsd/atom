package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.IMatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.GameSession;
import model.Player;

/**
 * Created by eugene on 12/19/16.
 */
public class LeavePlayerMsg extends Message {
  private final Player player;

  public LeavePlayerMsg(Player player) {
    super(null, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
    this.player = player;
  }

  @Override
  public void exec(Abonent abonent) {
    GameSession gameSession = ApplicationContext.instance().get(IMatchMaker.class).getHostGameSession(player);
    if(null != gameSession){
      gameSession.leave(player);
    }
  }
}
