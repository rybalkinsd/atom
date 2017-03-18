package ru.atom.thread.practice;

import ru.atom.thread.practice.Event.EventType;

import java.util.ArrayList;
import java.util.List;

import static ru.atom.thread.practice.Event.EventType.BAD;
import static ru.atom.thread.practice.Event.EventType.GOOD;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threads = new ArrayList<>();
        for (EventProducer event : eventProducers) {
            threads.add(new Thread(event));
        }
        threads.forEach(Thread::start);
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countEvents(GOOD);
    }

    public static long countTotalNumberOfBadEvents() {
        return countEvents(BAD);
    }

    private static long countEvents(EventType eventType) {
        long count = 0L;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == eventType) {
                count++;
            }
        }
        return count;
    }
}
