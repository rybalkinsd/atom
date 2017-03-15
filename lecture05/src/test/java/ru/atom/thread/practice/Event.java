package ru.atom.thread.practice;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class Event {
    private EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    enum EventType {
        GOOD, BAD
    }
}
