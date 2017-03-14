package ru.atom.thread.mm;

import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by sergey on 3/14/17.
 */
public class MatchMakerTest {
    private final ThreadFactory matchMakerThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Match-maker-%d")
            .build();


    private final ThreadFactory connectionProducerThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Connection-producer-%d")
            .build();

    @Test
    public void singleProducer() throws Exception {
        Thread connectionProducer = connectionProducerThreadFactory.newThread(new ConnectionProducer());
        Thread matchMaker = matchMakerThreadFactory.newThread(new MatchMaker());

        connectionProducer.start();
        matchMaker.start();

        Thread.sleep(10_000);

        connectionProducer.interrupt();
        matchMaker.interrupt();
    }

    @Test
    public void multipleProducer() throws Exception {
        List<Thread> producers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            producers.add(connectionProducerThreadFactory.newThread(new ConnectionProducer()));
        }

        Thread matchMaker = matchMakerThreadFactory.newThread(new MatchMaker());

        producers.forEach(Thread::start);
        matchMaker.start();

        Thread.sleep(10_000);

        producers.forEach(Thread::interrupt);
        matchMaker.interrupt();
    }

    @Test
    public void multipleProducerMultipleMatchMaker() throws Exception {
        List<Thread> producers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            producers.add(connectionProducerThreadFactory.newThread(new ConnectionProducer()));
        }

        List<Thread> matchMakers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            producers.add(matchMakerThreadFactory.newThread(new MatchMaker()));
        }

        matchMakers.forEach(Thread::start);
        producers.forEach(Thread::start);

        Thread.sleep(10_000);

        producers.forEach(Thread::interrupt);
        matchMakers.forEach(Thread::interrupt);
    }
}