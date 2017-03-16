package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>();

        for (EventProducer event : eventProducers) {
            threads.add(new Thread(event));
            threads.get(threads.size() - 1).start();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Execution is interrupted");
            }
        });
    }

    public static long countTotalNumberOfGoodEvents() {
        long countGood = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                countGood++;
            }
        }
        return countGood;
    }

    public static long countTotalNumberOfBadEvents() {
        long countBad = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                countBad++;
            }
        }
        return countBad;
    }
}
