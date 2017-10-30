package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    public static void produceEvents(List<EventProducer> eventProducers) {
        eventProducers.stream()
                .map(Thread::new)
                .peek(Thread::start).forEach(
                    thread -> {
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
    }

    public static long countTotalNumberOfGoodEvents() {
        return EventQueue.getInstance().stream()
                .filter(event -> event.getEventType() == Event.EventType.GOOD)
                .count();
    }

    public static long countTotalNumberOfBadEvents() {
        return EventQueue.getInstance().stream()
                .filter(event -> event.getEventType() == Event.EventType.BAD)
                .count();
    }
}
