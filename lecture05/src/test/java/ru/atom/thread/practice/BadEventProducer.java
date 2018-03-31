package ru.atom.thread.practice;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class BadEventProducer implements EventProducer {
    private final int numberOfEvents;

    public BadEventProducer(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfEvents; i++) {
            EventQueue.getInstance().offer(new Event(Event.EventType.BAD));
        }
    }
}
