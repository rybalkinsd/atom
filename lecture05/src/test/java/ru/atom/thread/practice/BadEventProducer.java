package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class BadEventProducer implements EventProducer {
    private final int numberOfEvents;
    private static final Logger log = LogManager.getLogger(BadEventProducer.class);

    public BadEventProducer(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfEvents; i++) {
            EventQueue.getInstance().offer(new Event(Event.EventType.BAD));
            log.info("Создано плохое событие" + (i + 1));
        }
    }
}
