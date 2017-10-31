package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static int GEPcount;
    private static int BEPcount;

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (int i = 0; i < eventProducers.size(); i++) {
            if (eventProducers.get(i) instanceof GoodEventProducer) {
                GoodEventProducer gep = (GoodEventProducer) eventProducers.get(i);
                Thread gt = new Thread(new GoodEventProducer(gep.getNumberOfEvents()));
                gt.setName("GT");
                gt.start();
                GEPcount += gep.getNumberOfEvents();
            }
            if (eventProducers.get(i) instanceof BadEventProducer) {
                BadEventProducer bep = (BadEventProducer) eventProducers.get(i);
                Thread bt = new Thread(new BadEventProducer(bep.getNumberOfEvents()));
                bt.setName("BT");
                bt.start();
                BEPcount += bep.getNumberOfEvents();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return GEPcount;
    }

    public static long countTotalNumberOfBadEvents() {
        return BEPcount;
    }
}
