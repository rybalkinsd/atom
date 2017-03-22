package ru.atom.thread.practice;


import java.util.ArrayList;
import java.util.Iterator;
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
        for (EventProducer ev : eventProducers) {
            threads.add(new Thread(ev));
        }

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                log.warn("InterruptedException");
            }
        }
    }


    public static long countTotalNumberOfGoodEvents() {
        Iterator it = EventQueue.getInstance().iterator();
        Event tmp = null;
        long count = 0;
        while (it.hasNext()) {
            tmp = (Event)it.next();
            if (tmp.getEventType() == Event.EventType.GOOD) {
                ++count;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        Iterator it = EventQueue.getInstance().iterator();
        Event tmp = null;
        long count = 0;
        while (it.hasNext()) {
            tmp = (Event)it.next();
            if (tmp.getEventType() == Event.EventType.BAD) {
                ++count;
            }
        }
        return count;
    }
}
