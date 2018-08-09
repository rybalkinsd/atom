package ru.atom.thread.practice;

import java.util.List;
import ru.atom.thread.practice.Event.EventType;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        eventProducers.forEach(producer -> {
            Thread producerThread = new Thread(producer);
            producerThread.start();
            try {
                producerThread.join();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
                e.printStackTrace();
            }
        });
    }

    public static long countTotalNumberOfGoodEvents() {
        return EventQueue.getInstance().parallelStream()
                .filter(event -> event.getEventType() == EventType.GOOD).count();
    }

    public static long countTotalNumberOfBadEvents() {
        return EventQueue.getInstance().parallelStream()
                .filter(event -> event.getEventType() == EventType.BAD).count();
    }
}
