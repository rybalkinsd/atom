package ru.atom.mm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.service.ConnectionProducer;
import ru.atom.mm.service.MatchMaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 3/14/17.
 */
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest
@Import(Config.class)
public class MatchMakerTest {

    @Autowired
    ConnectionProducer connectionProducer;

    @Test
    public void singleProducer() throws Exception {
        Thread connectionProducer = new Thread(new ConnectionProducer());
        connectionProducer.setName(ConnectionProducer.class.getSimpleName());

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName(MatchMaker.class.getSimpleName());

        connectionProducer.start();
        matchMaker.start();

        Thread.sleep(10_000);

        connectionProducer.interrupt();
        matchMaker.interrupt();
    }

    @Test
    public void multipleProducer() throws Exception {
        List<Thread> producers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Thread producer = new Thread(new ConnectionProducer());
            producer.setName(ConnectionProducer.class.getSimpleName() + " " + i);

            producers.add(producer);
        }

        Thread matchMaker = new Thread(new MatchMaker());
        matchMaker.setName(MatchMaker.class.getSimpleName());

        producers.forEach(Thread::start);
        matchMaker.start();

        Thread.sleep(10_000);

        producers.forEach(Thread::interrupt);
        matchMaker.interrupt();
    }

}