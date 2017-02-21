package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import leaderboard.Leaderboard;
import network.ClientConnectionServer;

/**
 * Created by s on 29.11.16.
 */
public class LeaderBoardMsg extends Message {

    public LeaderBoardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        ApplicationContext.instance().get(Leaderboard.class).sendleaders();
    }
}
