package ru.atom.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.object.Token;
import ru.atom.object.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*import org.intellij.lang.annotations.Language;*/

/**
 * Created by Fella on 16.04.2017.
 */

public class TokenDao implements Dao<Token> {

    private static final Logger log = LogManager.getLogger(TokenDao.class);

    /*@Language("sql")*/
    private static final String SELECT_ALL_TOKENS =
            "select * " +
                    "from bombergirl.token as t " +
                    "join bombergirl.user as u " +
                    "on t.iduser = u.id " +
                    "order by u.registrationDate;";

    /* @Language("sql")*/
    private static final String SELECT_ALL_TOKENS_WHERE =
            "select * " +
                    "from bombergirl.token as t " +
                    "join bombergirl.user as u " +
                    "on t.iduser = u.id " +
                    "where ";

    /*@Language("sql")*/
    private static final String INSERT_TOKENS_TEMPLATE =
            "insert into bombergirl.token (value, idUser) " +
                    "values ( '%d','%d');";

    /*@Language("sql")*/
    private static final String DELETE_TOKEN_WHERE =
            "delete from bombergirl.token where ";


    @Override
    public List<Token> getAll() {
        List<Token> tokens = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_TOKENS);
            while (rs.next()) {
                tokens.add(mapToToken(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll Tokens.", e);
            return Collections.emptyList();
        }

        return tokens;
    }

    public boolean delete(Token token) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            final String findByIdTokeCondition = " idToken=\'" + token.getIdToken() + "\'";
            String condition = String.join(" and ", findByIdTokeCondition);
            try {
                stm.executeQuery(DELETE_TOKEN_WHERE + condition);
            } catch (SQLException exception) {
                log.info("It Normalno SQLException when delete: executeQuery(DELETE) is empty");
            }
            return true;
        } catch (SQLException e) {
            log.error("Failed to Delete Tokens.", e);
            return false;
        }

    }


    @Override
    public List<Token> getAllWhere(String... conditions) {
        List<Token> tokens = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            String condition = String.join(" and ", conditions);
            ResultSet rs = stm.executeQuery(SELECT_ALL_TOKENS_WHERE + condition);
            while (rs.next()) {
                tokens.add(mapToToken(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAllWhere Tokens222.", e);
            return Collections.emptyList();
        }

        return tokens;
    }

    @Override
    public void insert(Token token) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(INSERT_TOKENS_TEMPLATE, token.getValueToken(), token.getUser().getIdUser()));
        } catch (SQLException e) {
            log.error("Failed to create token for user {}", token.getUser().getLogin(), e);
        }


    }

    private static Token mapToToken(ResultSet rs) throws SQLException {
        return new Token()
                .setIdToken(rs.getInt("idToken"))
                .setValueToken(rs.getLong("value"))
                .setUser(
                        new User()
                                .setIdUser(rs.getInt("id"))
                                .setLogin(rs.getString("login"))
                                .setPassword(rs.getString("password"))
                                .setRegistrationDate(rs.getTimestamp("registrationDate"))
                );
    }

}
