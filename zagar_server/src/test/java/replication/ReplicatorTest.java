package replication;

import main.ApplicationContext;
import main.MasterServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

/**
 * Created by roman on 27.11.16.
 */
public class ReplicatorTest {
    @NotNull
    private final static Logger log = LogManager.getLogger(ReplicatorTest.class);

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
    public void startWithTestReplicator(){
        try {
            MasterServerThread masterServerThread = new MasterServerThread();
            masterServerThread.start();
            Replicator testReplicator = new TestReplicator();
            while(ApplicationContext.instance().get(Replicator.class) == null){}
            ApplicationContext.instance().put(Replicator.class, testReplicator);
            Thread.currentThread().join();
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
