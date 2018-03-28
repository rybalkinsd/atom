package ru.atom.thread.practice;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        Collection<Thread> threads = new ArrayList<>();
        for(EventProducer prod: eventProducers){
            Thread thrd = new Thread(prod);
            thrd.start();
            threads.add(thrd);
        };
        for (Thread tmp : threads ){
            try {
                tmp.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public static long countTotalNumberOfGoodEvents() {
        return EventQueue.getInstance().stream().filter(Event::isGood).count();
    }

    public static long countTotalNumberOfBadEvents() {
        return EventQueue.getInstance().stream().filter(Event::isGood).count();
    }
}
