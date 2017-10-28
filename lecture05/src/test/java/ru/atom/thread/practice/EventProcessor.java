package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer e : eventProducers) {
			e.run();
		}
    }

    public static long countTotalNumberOfGoodEvents() {
        int number = 0;
		for (Event currentevent : EventQueue.getInstance()) {
			if (currentevent.getEventType() == Event.EventType.GOOD) {
				number++;
			}
		}
		return number;
    }

    public static long countTotalNumberOfBadEvents() {
        int number = 0;
		for (Event currentevent : EventQueue.getInstance()) {
			if (currentevent.getEventType() == Event.EventType.BAD) {
				number++;
			}
		}
		return number;
    }
}
