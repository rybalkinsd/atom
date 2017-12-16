package ru.atom.bd;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sergey on 3/25/17.
 */
public class UserDao implements Dao<Gamesession> {
    private static final Logger log = LogManager.getLogger(UserDao.class);


    @Override
    public List<Gamesession> getAll() {
        List<Gamesession> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery("select * " +
                    "from players");
            while (rs.next()) {
                persons.add(mapToUser(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return persons;
    }

    @Override
    public List<Gamesession> getAllWhere(String... conditions) {
        List<Gamesession> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {

            String condition = String.join(" and ", conditions);
            ResultSet rs = stm.executeQuery("select * " +
                    "from players where " + condition);
            while (rs.next()) {
                persons.add(mapToUser(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll where.", e);
            return Collections.emptyList();
        }

        return persons;
    }

    @Override
    public void insert(Gamesession gamesession) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format("insert into players values ('" + gamesession.getId() + "','"
                    + gamesession.getFirstname() + "','" + gamesession.getSecondname() + "');"));
            System.out.println("smth");
        } catch (SQLException e) {
            log.error("Failed to getAll where.", e);
    
        }
    }




    private static Gamesession mapToUser(ResultSet rs) throws SQLException {
        return new Gamesession(rs.getInt("id"), rs.getString("firstname"), rs.getString("secondname"));


    }
}
