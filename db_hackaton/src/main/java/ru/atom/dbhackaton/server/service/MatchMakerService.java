package ru.atom.dbhackaton.server.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.mm.Connection;
import ru.atom.dbhackaton.server.mm.ThreadSafeQueue;

import java.util.concurrent.ConcurrentHashMap;


public class MatchMakerService {
    private static final Logger logger = LogManager.getLogger(MatchMakerService.class);
    private static ConcurrentHashMap<String, Connection> joins = new ConcurrentHashMap<>();

    public long join(String name, String token) {
        Connection connection;
        if (!joins.containsKey(name)) {
            connection = new Connection(token, name);
            joins.put(name, connection);
            ThreadSafeQueue.getInstance().offer(connection);
        } else {
            connection = joins.get(name);
        }
        if (connection.idNull()) {
            return -1;
        }
//        joins.remove(name);
        return connection.getSessionIdValue();
    }

}
