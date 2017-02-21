package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Configurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class DbConnector {
    private static final Logger log = LogManager.getLogger(DbConnector.class);

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
    private static final String URL;
    private static final String HOST;
    private static final int PORT;
    private static final String DB_NAME;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Failed to load jdbc driver.", e);
            System.exit(-1);
        }

        HOST = Configurations.getStringProperty("DbHost");
        PORT = Configurations.getIntProperty("DbPort");
        DB_NAME=Configurations.getStringProperty("DbName");
        USER=Configurations.getStringProperty("DbUser");
        PASSWORD=Configurations.getStringProperty("DbPassword");
        URL = String.format(URL_TEMPLATE, HOST, PORT, DB_NAME);
        log.info("Success. DbConnector init.");
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private DbConnector() { }
}