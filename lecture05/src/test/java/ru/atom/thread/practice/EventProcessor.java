package ru.atom.thread.practice;

import java.util.List;
import java.util.ArrayList;
/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>();

        for (EventProducer produce : eventProducers) {
            threads.add(new Thread(produce));
        }

        for (Thread thread : threads) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0;

        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        long count = 0;

        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}
