package ru.atom.thread.mm;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 3/14/17.
 */
@Ignore
public class MatchMakerTest {

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