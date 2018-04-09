package ru.atom.dao;

/**
 * Created by sergey on 3/25/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("BEGIN;\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS user;\n" +
                    "CREATE TABLE user (\n" +
                    "  id    SERIAL             NOT NULL,\n" +
                    "  login VARCHAR(20) UNIQUE NOT NULL,\n" +
                    "\n" +
                    "  PRIMARY KEY (id)\n" +
                    ");\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS message;\n" +
                    "CREATE TABLE message (\n" +
                    "  id     SERIAL       NOT NULL,\n" +
                    "  \"user\" INTEGER      NOT NULL REFERENCES user ON DELETE CASCADE,\n" +
                    "  time   TIMESTAMP    NOT NULL,\n" +
                    "  value  VARCHAR(140) NOT NULL,\n" +
                    "\n" +
                    "  PRIMARY KEY (id)\n" +
                    ");\n" +
                    "\n" +
                    "COMMIT;\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private DbConnector() {
    }
}