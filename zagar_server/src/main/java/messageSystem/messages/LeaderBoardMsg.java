package messageSystem.messages;

import main.ApplicationContext;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import statistic.LeaderBoard;

public class LeaderBoardMsg extends Message {

    public LeaderBoardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class)
                .getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        ApplicationContext.instance().get(LeaderBoard.class).getLeaders(0);
    }
}