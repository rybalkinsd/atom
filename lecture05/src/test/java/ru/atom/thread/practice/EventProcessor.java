package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threadsEventProducers = new ArrayList<>();

        for (EventProducer evTread: eventProducers) {
            threadsEventProducers.add(new Thread(evTread));
            log.info("Thread created");
        }
        threadsEventProducers.forEach(Thread::start);
        log.info("Threads started");

        for (Thread thread: threadsEventProducers) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                log.warn("InterruoedException catched" + thread.getName());
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long summair = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                summair += 1;
            }
        }
        return summair;
    }

    public static long countTotalNumberOfBadEvents() {
        long summair = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                summair += 1;
            }
        }
        return summair;
    }
}
