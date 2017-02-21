package messageSystem.messages;

import leaderboard.Leaderboard;
import main.ApplicationContext;
import main.Service;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;

/**
 * Created by svuatoslav on 11/28/16.
 */
public class UpdateLeaderboardMsg extends Message {
    public UpdateLeaderboardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(Leaderboard.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        ApplicationContext.instance().get(MessageSystem.class).getService(Leaderboard.class).update();
    }
}
