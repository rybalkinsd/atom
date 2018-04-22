package ru.atom.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atom.model.User;

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
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private static final  String SELECT_USER_WHERE =
            "select * " +
                    "from chat.user " +
                    "where login = '%s'";

    private static final String SELECT_ALL_USERS =
            "select * " +
                    "from chat.user";

    private static final String SELECT_ALL_USERS_WHERE =
            "select * " +
                    "from chat.user " +
                    "where ";

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
        User us = null;
        try (Connection con = DbConnector.getConnection();
            Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_USER_WHERE, name));
            rs.next();
            us = mapToUser(rs);
        }
        catch (SQLException exc) {
            log.error("Failed to get user by name");
        }

        return us;
    }

    private static User mapToUser(ResultSet rs) throws SQLException {
        return new User()
                .setId(rs.getInt("id"))
                .setLogin(rs.getString("login"));
    }
}
