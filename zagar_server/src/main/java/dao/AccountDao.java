package dao;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDao {
    private static final Logger log = LogManager.getLogger(AccountDao.class);

    private static final String INSERT_TEMPLATE =
            "INSERT INTO accounts (\"user\", password) VALUES ('%s', '%s');";

    private static final String GET_TEMPLATE =
            "SELECT * FROM accounts WHERE \"user\"='%s';";

    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE IF NOT EXISTS accounts " +
            "(" +
            " ID serial PRIMARY KEY NOT NULL," +
            "  \"user\" character varying(10)," +
            "  password character varying(10) )";

    public void insert(Pair<String, String> pair) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            createTable(con);
            stm.execute(String.format(INSERT_TEMPLATE, pair.getKey(), pair.getValue()));
        } catch (SQLException e) {
            log.error("Failed to add record {}", pair, e);
        }
    }

    private void createTable(Connection con) throws SQLException
    {
        Statement stm = con.createStatement();
        stm.execute(CREATE_TABLE_TEMPLATE);
    }

    public Pair<String,String> getByUser(String... conditions) {
        Pair<String,String> res=null;
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(GET_TEMPLATE, conditions));
            if (rs.next()) {
                res=new Pair<>(rs.getString("user"),rs.getString("password"));
            }
        } catch (SQLException e) {
            log.error("Failed to getN.", e);
        }
        return res;
    }
}