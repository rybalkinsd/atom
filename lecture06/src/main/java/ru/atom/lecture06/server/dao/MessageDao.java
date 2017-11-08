package ru.atom.lecture06.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;
import ru.atom.lecture06.server.model.Message;
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
public class MessageDao implements Dao<Message> {
    private static final Logger log = LogManager.getLogger(MessageDao.class);

    @Language("sql")
    private static final String SELECT_ALL_MESSAGES =
            "select m.time, m.value, u.* " +
                    "from chat.message as m " +
                    "join chat.user as u " +
                    "  on m.user = u.id " +
                    "order by m.time";

    @Language("sql")
    private static final String INSERT_MESSAGE_TEMPLATE =
            "insert into chat.message (\"user\", time, value) " +
                    "values (%d, now(), '%s')";

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_MESSAGES);
            while (rs.next()) {
                messages.add(mapToMessage(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return messages;
    }

    @Override
    public List<Message> getAllWhere(String... conditions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(Message message) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(INSERT_MESSAGE_TEMPLATE, message.getUser().getId(), message.getValue()));
        } catch (SQLException e) {
            log.error("Failed to create message {}", message, e);
        }
    }

    private static Message mapToMessage(ResultSet rs) throws SQLException {
        return new Message()
                .setTimestamp(rs.getDate("time"))
                .setValue(rs.getString("value"))
                .setUser(
                        new User()
                                .setId(rs.getInt("id"))
                                .setLogin(rs.getString("login"))
                );
    }
}
