package ru.atom.thread.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static long count = 0;

    public static void produceEvents(List<EventProducer> eventProducers) throws Exception {
        List<Thread> producers = new ArrayList<>();
        for (int i = 0; i < eventProducers.size(); i++) {
            Thread producer = new Thread(eventProducers.get(i));
            producer.setName(EventProducer.class.getSimpleName() + " " + i);
            producers.add(producer);
        }
        producers.forEach(Thread::start);

        Thread.sleep(10_000);

        producers.forEach(Thread::interrupt);
    }

    public static long countTotalNumberOfGoodEvents() {
        count = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType().equals(Event.EventType.GOOD)) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        count = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType().equals(Event.EventType.BAD)) {
                count++;
            }
        }
        return count;
    }
}