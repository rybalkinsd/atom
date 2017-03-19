package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (EventProducer producer: eventProducers)
            threads.add(new Thread(producer));
        for (Thread currentThread: threads) {
            currentThread.start();
            try {
                currentThread.join();
            } catch (InterruptedException e){
                log.info("Interrupted");
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long NumberOfGoodEvents = 0;
        for (Event newEvent:EventQueue.getInstance()) {
            if (newEvent.getEventType() == Event.EventType.GOOD)
                NumberOfGoodEvents++;
            }
        return NumberOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long NumberOfBadEvents = 0;
        for (Event newEvent:EventQueue.getInstance()) {
            if (newEvent.getEventType() == Event.EventType.GOOD)
                NumberOfBadEvents++;
        }
        return NumberOfBadEvents;
    }
}
