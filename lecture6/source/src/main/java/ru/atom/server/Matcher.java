package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.dao.LikeDao;
import ru.atom.model.dao.MatchDao;
import ru.atom.model.data.Match;

import java.util.List;
import java.util.stream.Stream;


public class Matcher implements Runnable {
    private static final Logger log = LogManager.getLogger(Matcher.class);

    private MatchDao matchDao = new MatchDao();
    private LikeDao likesDao = new LikeDao();

    @Override
    public void run() {
        while (true) {
            List<Match> matches = likesDao.getMatches();
            matches.stream()
                    .flatMap(m -> Stream.of(m, m.reverse()))
                    .forEach(m -> matchDao.insert(m));
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                log.error("Matcher was interrupted.", e);
            }
        }
    }
}
