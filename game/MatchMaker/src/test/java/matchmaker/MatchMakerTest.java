package matchmaker;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchMakerTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ConcurrentHashMap<Long,Integer> returnedRequests;

    private static final int NUMBER_OF_REQESTS = 16;

    @Test
    public void matchMakerTest() throws InterruptedException{
        Collection<Thread> list = new LinkedList<>();
        int rank = 5;
        for (int i = 0;i < NUMBER_OF_REQESTS;i++) {
            if (i % 4 == 0 && i > 0)
                rank += 10;
            TestClient client = ctx.getBean(TestClient.class);
            client.setRank(rank);
            Thread thread = new Thread(client);
            list.add(thread);
            thread.start();
        }
        for (Thread thread:list)
            thread.join();
        int ctr = 0;
        for (Long key: returnedRequests.keySet())
            ctr += returnedRequests.get(key);
        Assert.assertTrue(ctr == NUMBER_OF_REQESTS);
    }

    @Test
    @Ignore
    public void matchMakerIncompleteSessionTest() throws InterruptedException{
        Collection<Thread> list = new LinkedList<>();
        for (int i = 0;i < 7;i++) {
            list.add(new Thread(new TestClient()));
        }
        for (Thread thread:list)
            thread.start();
        for (Thread thread:list)
            thread.join();
    }





}
