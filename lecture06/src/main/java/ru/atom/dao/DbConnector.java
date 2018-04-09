package ru.atom.dao;

/**
 * Created by sergey on 3/25/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class DbConnector {
    private static final Logger log = LoggerFactory.getLogger(DbConnector.class);

    //private static final String URL_TEMPLATE = "jdbc:h2://%s:%d/%s";
    static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "atom";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Failed to load jdbc driver.", e);
            System.exit(-1);
        }
        log.info("Success. DbConnector init.");
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private DbConnector() { }
}