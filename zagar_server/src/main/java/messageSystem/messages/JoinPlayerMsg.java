package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.IMatchMaker;
import mechanics.Mechanics;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import model.Player;

import javax.validation.constraints.NotNull;

/**
 * Created by eugene on 12/19/16.
 */
public class JoinPlayerMsg extends Message {
  private final Player player;

  public JoinPlayerMsg(@NotNull Player player) {
    super(null, ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress());
    this.player = player;
  }

  @Override
  public void exec(Abonent abonent) {
    ApplicationContext.instance().get(IMatchMaker.class).joinGame(player);
  }
}
