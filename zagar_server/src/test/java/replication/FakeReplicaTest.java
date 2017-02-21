package replication;

import main.MasterServer;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ivan on 25.11.16.
 */
public class FakeReplicaTest {

    @Ignore
    @Test
    public void fakeLeaderBoardTest() throws Exception {

//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("fake_leaders.json").getFile());
//        System.out.println(file.getAbsolutePath());
//
//        FakeLeaderBoardReplicator replicator = new FakeLeaderBoardReplicator();
//        replicator.replicate();

        MasterServer.main(new String[]{"test", "src/test/resources/fake_leader_board_config.ini"});
    }

    @Ignore
    @Test
    public void fullState() throws Exception{
      MasterServer.main(new String[]{"test", "src/test/resources/fake_replica_config.ini"});
    }
}
