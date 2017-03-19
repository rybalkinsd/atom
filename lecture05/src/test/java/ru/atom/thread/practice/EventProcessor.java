package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author apomosov
 * @since 15.03.17
 */

public class EventProcessor {
    public static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> producers = new ArrayList<>();
        for (EventProducer eventProducer : eventProducers) {
            producers.add(new Thread(eventProducer));
        }
        for (Thread eventThread : producers) {
            eventThread.start();
        }
        for (Thread eventThread : producers) {
            try {
                eventThread.join();
            } catch (InterruptedException ex) {
                log.warn("InterruptedException!");
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long counter = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                counter++;
            }
        }
        return counter;
    }

    public static long countTotalNumberOfBadEvents() {
        long counter = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                counter++;
            }
        }
        return counter;
    }
}
