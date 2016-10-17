package ru.atom.model.dao;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.Gender;
import ru.atom.model.person.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class PersonDao {
    private static final Logger log = LogManager.getLogger(PersonDao.class);

    public static final String SELECT_ALL_PERSONS =
            "SELECT * FROM PERSONS";

    public static final String SELECT_PERSONS_WHERE_TEMPLATE =
            "SELECT * FROM PERSONS WHERE %s=%s";


    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_PERSONS);
            while (rs.next()) {
                persons.add(mapToPerson(rs));
            }
        } catch (SQLException e) {
            log.warn("Failed get all.", e);
            return Collections.emptyList();
        }
        return null;
    }

    /**
     * SELECT * ... WHERE cond0 AND ... AND condN
     * @param conditions
     * @return
     */
    public List<Person> getAllWhere(String ... conditions) {
        throw new NotImplementedException();
    }

    public Optional<Person> findById(int id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }

    private static Person mapToPerson(ResultSet rs) throws SQLException {
        return new Person()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setGender(Gender.valueOf(rs.getString("gender")))
                .setAge(rs.getInt("age"));
    }
}
