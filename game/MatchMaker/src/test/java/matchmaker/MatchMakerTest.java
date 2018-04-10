package matchmaker;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;



@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan
public class MatchMakerTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired

    private ConcurrentHashMap<Long,Integer> returnedRequests;

    private static final int NUMBER_OF_REQESTS_TEST_1 = 16;

    private static final int NUMBER_OF_REQESTS_TEST_2 = 8;

    @Test
    public void matchMakerTest() throws InterruptedException{
        returnedRequests.clear();
        Collection<Thread> list = new LinkedList<>();
        int rank = 5;
        for (int i = 0;i < NUMBER_OF_REQESTS_TEST_1;i++) {
            if (i % 4 == 0 && i > 0)
                rank += 10;
            TestClient client = ctx.getBean(TestClient.class);
            client.setRank(rank);
            Thread thread = new Thread(client);
            list.add(thread);
            thread.start();
        }
        int ctr2 = 0;
        for (Thread thread:list)
            thread.join();
        for (Long key: returnedRequests.keySet())
            ctr2 += returnedRequests.get(key);
        System.out.println(ctr2);

        Assert.assertTrue(ctr2 == NUMBER_OF_REQESTS_TEST_1);
    }


    @Test
    public void matchMakerIncompleteSessionTest() throws InterruptedException{
        returnedRequests.clear();
        Collection<Thread> list = new LinkedList<>();
        int rank = 5;
        for (int i = 0;i < NUMBER_OF_REQESTS_TEST_2;i++) {
            if(i % 3 == 0 && i > 0)
                Thread.sleep(12_000);
            TestClient client = ctx.getBean(TestClient.class);
            client.setRank(rank);
            Thread thread = new Thread(client);
            list.add(thread);
            thread.start();
        }
        int ctr2 = 0;
        for (Thread thread:list)
            thread.join();
        for (Long key: returnedRequests.keySet())
            ctr2 += returnedRequests.get(key);
        System.out.println(ctr2);
        Assert.assertTrue(ctr2 == NUMBER_OF_REQESTS_TEST_2);
    }





}
