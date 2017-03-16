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
        eventProducers.forEach(eventProducer ->
                threads.add(new Thread(eventProducer)));
        threads.forEach(Thread::start);

        do {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }
        } while (threads.stream().anyMatch(Thread::isAlive));
    }

    public static long countTotalNumberOfGoodEvents() {
        return EventQueue.getInstance()
                .stream()
                .filter(event -> event.getEventType() == Event.EventType.GOOD)
                .count();
    }

    public static long countTotalNumberOfBadEvents() {
        return EventQueue.getInstance()
                .stream()
                .filter(event -> event.getEventType() == Event.EventType.BAD)
                .count();
    }
}
