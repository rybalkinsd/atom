package ru.atom.thread.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threads = new ArrayList<>();

        for (EventProducer ep : eventProducers) {
            threads.add(new Thread(ep));
        }

        threads.forEach(Thread::start);

        for (Thread tempThread : threads) {
            if (!tempThread.isInterrupted()) {
                try {
                    tempThread.join();
                } catch (InterruptedException ignored) {
                    ;
                }
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long countOfGoodEvents = 0;
        for (Event tempEvent : EventQueue.getInstance()) {
            if (tempEvent.getEventType() == Event.EventType.GOOD) {
                countOfGoodEvents++;
            }
        }
        return countOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long countOfBadEvents = 0;
        for (Event tempEvent : EventQueue.getInstance()) {
            if (tempEvent.getEventType() == Event.EventType.BAD) {
                countOfBadEvents++;
            }
        }
        return countOfBadEvents;
    }
}
