package statistic;

import main.ApplicationContext;
import main.MasterServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

/**
 * Created by roman on 28.11.16.
 */
public class LeaderBoardTest {
    @NotNull
    private final static Logger log = LogManager.getLogger(LeaderBoardTest.class);

    private class MasterServerThread extends Thread{
        @Override
        public void run(){
            try{
                MasterServer.main(new String[0]);
            } catch (Exception e){
                log.info(e.getMessage());
            }
        }
    }

    @Test
    public void startWithTestLeaderBoard(){
        try {
            LeaderBoardTest.MasterServerThread masterServerThread = new LeaderBoardTest.MasterServerThread();
            masterServerThread.start();
            LeaderBoard testLeaderBoard = new TestLeaderBoard();
            while(ApplicationContext.instance().get(LeaderBoard.class) == null){}
            ApplicationContext.instance().put(LeaderBoard.class, testLeaderBoard);
            Thread.currentThread().join();
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
