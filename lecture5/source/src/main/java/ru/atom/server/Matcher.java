package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.dao.MatchDao;
import ru.atom.model.data.Match;

import java.util.List;
import java.util.stream.Stream;


public class Matcher implements Runnable {
    private static final Logger log = LogManager.getLogger(Matcher.class);

    private static int lastProcessedMatchId = -1;

    private MatchDao match = new MatchDao();

    @Override
    public void run() {
        while (true) {
            List<Match> matches = match.getAllWhere("id > " + lastProcessedMatchId);
            matches.stream()
                    .flatMap(m -> Stream.of(m, m.reverse()))
                    .forEach(this::notify);

            lastProcessedMatchId = matches.stream()
                    .flatMap(m -> Stream.of(m.getA(), m.getB()))
                    .max(Integer::compareTo)
                    .orElse(lastProcessedMatchId);

            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                log.error("Matcher was interrupted.", e);
            }
        }
    }

    private void notify(Match match) {
        log.info("! Notify {} about match with {}", match.getA(), match.getB());
    }
}
