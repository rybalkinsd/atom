package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.thread.instantiation.ThreadCreationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    private static final Logger log = LogManager.getLogger(EventProcessor.class);

    static long countOfGoodEvents;
    static long countOfBadEvents;

    public static void produceEvents(List<EventProducer> eventProducers) {

        List<Thread> trList = new ArrayList<>();

        for (EventProducer eventProducer :eventProducers) {
            Thread eventProducerTr = new Thread(eventProducer);
            trList.add(eventProducerTr);
            eventProducerTr.start();
        }

        QueueProcessor qp = new QueueProcessor();
        qp.start();

        for (Thread trItem: trList) {
            try {
                trItem.join();
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        qp.interrupt();

        try {
            qp.join();
        } catch (InterruptedException e) {
            log.error(e);
        }

        countOfBadEvents = qp.getCountOfBadEvents();
        countOfGoodEvents = qp.getCountOfGoodEvents();
    }

    public static long countTotalNumberOfGoodEvents() {
        return countOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        return countOfBadEvents;
    }
}
