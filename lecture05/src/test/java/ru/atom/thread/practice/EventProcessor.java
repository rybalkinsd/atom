package ru.atom.thread.practice;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>(eventProducers.size());
        for (EventProducer e : eventProducers) {
            threads.add(new Thread(e));
        }

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.warn("InterruptedException" + e.getMessage() + t.getName());
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long res = 0;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == Event.EventType.GOOD) {
                res++;
            }
        }
        return res;
    }

    public static long countTotalNumberOfBadEvents() {
        long res = 0;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == Event.EventType.BAD) {
                res++;
            }
        }
        return res;
    }
}
