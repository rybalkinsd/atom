package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>(eventProducers.size());
        for (EventProducer producer : eventProducers) {
            threads.add(new Thread(producer));
        }

        threads.forEach(Thread::start);

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            ;
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countTotalNumberOfEventsOfType(Event.EventType.GOOD);
    }

    public static long countTotalNumberOfBadEvents() {
        return countTotalNumberOfEventsOfType(Event.EventType.BAD);
    }

    public static long countTotalNumberOfEventsOfType(Event.EventType type) {
        long result = 0;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == type) result++;
        }
        return result;
    }
}
