package accountserver.database.leaderboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class JdbcDbConnector {
    private static final Logger log = LogManager.getLogger(JdbcDbConnector.class);

    private static final String URL = "jdbc:postgresql://54.224.37.210/atom43_tinderdb";
    private static final String USER = "atom43";
    private static final String PASSWORD = "atom43";
    private static final String DRIVER = "org.postgresql.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("Failed to load jdbc driver.", e);
            System.exit(-1);
        }

        log.info("Success. DbConnector init.");
    }

    private JdbcDbConnector() {
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
