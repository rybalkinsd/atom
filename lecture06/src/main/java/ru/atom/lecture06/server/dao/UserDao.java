package ru.atom.lecture06.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;
import ru.atom.lecture06.server.model.User;

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
public class UserDao implements Dao<User> {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    @Language("sql")
    private static final String SELECT_ALL_USERS =
            "select * " +
                    "from chat.user";

    @Language("sql")
    private static final String SELECT_ALL_USERS_WHERE =
            "select * " +
                    "from chat.user " +
                    "where ";

    @Language("sql")
    private static final String INSERT_USER_TEMPLATE =
            "insert into chat.user (login) " +
                    "values ('%s');";

    @Override
    public List<User> getAll() {
        List<User> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_USERS);
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
    public List<User> getAllWhere(String... conditions) {
        List<User> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {

            String condition = String.join(" and ", conditions);
            ResultSet rs = stm.executeQuery(SELECT_ALL_USERS_WHERE + condition);
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
    public void insert(User user) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(INSERT_USER_TEMPLATE, user.getLogin()));
        } catch (SQLException e) {
            log.error("Failed to create user {}", user.getLogin(), e);
        }
    }

    public User getByName(String name) {
        throw new UnsupportedOperationException();
    }

    private static User mapToUser(ResultSet rs) throws SQLException {
        return new User()
                .setId(rs.getInt("id"))
                .setLogin(rs.getString("login"));
    }
}
