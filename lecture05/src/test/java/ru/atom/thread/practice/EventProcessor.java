package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @author apomosov
 * @since 15.03.17
 */

public class EventProcessor {
    private static Logger logger = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threads = new ArrayList<>();
        for (EventProducer eventProducer: eventProducers) {
            Thread thread = new Thread(eventProducer);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.info("{} was interrupted!", thread.getName());
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long numberOfGoodEvents = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType().equals(Event.EventType.GOOD))
                numberOfGoodEvents++;
        }
        return numberOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long numberOfBadEvents = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType().equals(Event.EventType.BAD))
                numberOfBadEvents++;
        }
        return numberOfBadEvents;
    }
}
