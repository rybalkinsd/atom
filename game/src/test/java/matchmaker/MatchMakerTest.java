package matchmaker;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MatchMakerTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MatchMakerTest.class);

    private static final int NUMBER_OF_REQESTS_TEST_1 = 16;

    private static final int NUMBER_OF_REQESTS_TEST_2 = 8;

    public static final CountDownLatch Start = new CountDownLatch(NUMBER_OF_REQESTS_TEST_1);

    @Test
    public void matchMakerTest() throws InterruptedException {
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
        for (Thread thread:list) {
            ctr2++;
            thread.join();
        }

        log.info("Counter: " + ctr2);

        Assert.assertTrue(ctr2 == NUMBER_OF_REQESTS_TEST_1);
    }


    @Test
    public void matchMakerIncompleteSessionTest() throws InterruptedException {
        Collection<Thread> list = new LinkedList<>();
        int rank = 5;
        for (int i = 0;i < NUMBER_OF_REQESTS_TEST_2;i++) {
            if (i % 3 == 0 && i > 0)
                Thread.sleep(12_000);
            TestClient client = ctx.getBean(TestClient.class);
            client.setRank(rank);
            Thread thread = new Thread(client);
            list.add(thread);
            thread.start();
        }
        int ctr2 = 0;
        for (Thread thread:list) {
            ctr2++;
            thread.join();
        }

        log.info("Counter: " + Integer.toString(ctr2));
        Assert.assertTrue(ctr2 == NUMBER_OF_REQESTS_TEST_2);
    }





}
